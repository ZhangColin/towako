package com.towako.traffic.channel;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.system.user.application.UserAppService;
import com.towako.system.user.request.CreateAccountCommand;
import com.towako.system.user.response.UserDetailDto;
import com.towako.traffic.channel.request.ChannelParam;
import com.towako.traffic.channel.request.ChannelQuery;
import com.towako.traffic.channel.response.ChannelBaseInfoConverter;
import com.towako.traffic.channel.response.ChannelBaseInfoDto;
import com.towako.traffic.channel.response.ChannelConverter;
import com.towako.traffic.channel.response.ChannelDto;
import com.towako.traffic.recommend.RecommendAppService;
import com.towako.traffic.wechatqrcode.WeChatQrCodeAppService;
import com.towako.traffic.wechatqrcode.response.WeChatQrCodeDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;
import static com.google.common.primitives.Longs.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class ChannelAppService {
    public static final String ERR_NAME_EXISTS = "渠道已存在。";

    private final ChannelRepository repository;
    private final WeChatQrCodeAppService weChatQrCodeAppService;
    private final SnowflakeIdWorker idWorker;
    private final UserAppService userAppService;
    private final RecommendAppService recommendAppService;

    private final ChannelConverter channelConverter = ChannelConverter.CONVERTER;

    public ChannelAppService(ChannelRepository repository, WeChatQrCodeAppService weChatQrCodeAppService,
                             UserAppService userAppService, RecommendAppService recommendAppService,
                             SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.weChatQrCodeAppService = weChatQrCodeAppService;
        this.idWorker = idWorker;
        this.userAppService = userAppService;
        this.recommendAppService = recommendAppService;
    }

    public PageResult<ChannelDto> searchChannels(@NonNull ChannelQuery channelQuery, @NonNull Pageable pageable) {
        final Page<Channel> searchResult = repository.findAll(querySpecification(channelQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        final List<ChannelDto> channels = channelConverter.convert(searchResult.getContent());

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(
                channels.stream().map(ChannelDto::getId).collect(toList()));

        channels.forEach(channelDto -> qrCodeDtos.stream()
                .filter(qrCodeDto -> qrCodeDto.getChannelId().equals(channelDto.getId()))
                .findFirst().ifPresent(qrCodeDto -> {
                    channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

                    channelDto.setTicket(qrCodeDto.getTicket());
                    channelDto.setImageUrl(qrCodeDto.getImageUrl());
                    channelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
                    channelDto.setUrl(qrCodeDto.getUrl());
                }));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                channels);
    }

    public List<ChannelBaseInfoDto> findAllEffectiveChannels() {
        return ChannelBaseInfoConverter.CONVERTER.convert(repository.findByStatus(1));
    }

    public Optional<ChannelDto> findByUserId(Long userId) {
        return repository.findByUserId(userId).map(channelConverter::convert);
    }

    public Optional<ChannelDto> findById(Long channelId) {
        return repository.findById(channelId).map(channelConverter::convert);
    }


    public ChannelDto getChannelByUserId(Long userId) {
        final ChannelDto channelDto = channelConverter.convert(requirePresent(repository.findByUserId(userId)));

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(asList(channelDto.getId()));

        channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

        qrCodeDtos.stream().filter(qrCodeDto -> qrCodeDto.getChannelId().equals(channelDto.getId()))
                .findFirst().ifPresent(qrCodeDto -> {
            channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

            channelDto.setTicket(qrCodeDto.getTicket());
            channelDto.setImageUrl(qrCodeDto.getImageUrl());
            channelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
            channelDto.setUrl(qrCodeDto.getUrl());
        });
        return channelDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public void addChannel(ChannelParam channelParam) {
        if (repository.existsByName(channelParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final long channelId = idWorker.nextId();

        weChatQrCodeAppService.applyWechatQrCode(channelId);

        final UserDetailDto account = createUserAccount(channelParam.getName(), channelParam.getPhone(), channelParam.getType());

        final Channel channel = new Channel(channelId,
                Optional.ofNullable(channelParam.getParentId()).orElse(0L),
                account.getId(), channelParam.getName(), channelParam.getPhone(),
                channelParam.getType());
        repository.save(channel);
    }

    private UserDetailDto createUserAccount(String name, String phone, String type) {
        final CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setUsername(phone);
        createAccountCommand.setPhone(phone);
        createAccountCommand.setRealName(name);
        createAccountCommand.setOrganizationIds(asList(1377345482606645249L));
        Long roleId = 0L;
        if (type.equals(ChannelType.DOCTOR)) {
            roleId = 3L;
        } else if (type.equals(ChannelType.FAMILY_HOTEL)) {
            roleId = 4L;
        } else if (type.equals(ChannelType.OTHER)) {
            roleId = 5L;
        } else {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("渠道类型不正确"));
        }
        createAccountCommand.setRoleIds(asList(roleId));
        final UserDetailDto account = userAppService.createAccount(createAccountCommand);
        return account;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createAccount(Long id) {
        final Channel channel = requirePresent(repository.findById(id));
        if (channel.getUserId() == null) {
            final UserDetailDto userAccount = createUserAccount(channel.getName(), channel.getPhone(), channel.getType());
            channel.setUserId(userAccount.getId());

            repository.save(channel);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void editChannel(Long id, ChannelParam channelParam) {
        if (repository.existsByNameAndIdNot(channelParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Channel channel = requirePresent(repository.findById(id));

        channel.describe(Optional.ofNullable(channelParam.getParentId()).orElse(0L),
                channelParam.getName(), channelParam.getPhone(), channelParam.getType());

        repository.save(channel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeChannel(long id) {
        repository.deleteById(id);
    }


    @Transactional(rollbackOn = Exception.class)
    public void enable(Long id) {
        final Channel channel = requirePresent(repository.findById(id));
        channel.enable();
        repository.save(channel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void disable(Long id) {
        final Channel channel = requirePresent(repository.findById(id));
        channel.disable();
        repository.save(channel);
    }
}
