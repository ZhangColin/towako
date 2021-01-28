package com.towako.channel.channel;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.channel.channel.request.ChannelParam;
import com.towako.channel.channel.request.ChannelQuery;
import com.towako.channel.channel.response.ChannelConverter;
import com.towako.channel.channel.response.ChannelDto;
import com.towako.channel.wechatqrcode.WeChatQrCodeAppService;
import com.towako.channel.wechatqrcode.response.WeChatQrCodeDto;
import com.towako.system.user.application.UserAppService;
import com.towako.system.user.request.CreateAccountCommand;
import com.towako.system.user.response.UserDetailDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    private final ChannelConverter channelConverter = ChannelConverter.CONVERTER;

    public ChannelAppService(ChannelRepository repository, WeChatQrCodeAppService weChatQrCodeAppService, SnowflakeIdWorker idWorker, UserAppService userAppService) {
        this.repository = repository;
        this.weChatQrCodeAppService = weChatQrCodeAppService;
        this.idWorker = idWorker;
        this.userAppService = userAppService;
    }

    public PageResult<ChannelDto> searchChannels(@NonNull ChannelQuery channelQuery, @NonNull Pageable pageable) {
        final Page<Channel> searchResult = repository.findAll(querySpecification(channelQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        final List<ChannelDto> doctors = channelConverter.convert(searchResult.getContent());

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(
                doctors.stream().map(ChannelDto::getId).collect(toList()));

        doctors.forEach(channelDto -> qrCodeDtos.stream()
                .filter(qrCodeDto->qrCodeDto.getChannelId().equals(channelDto.getId()))
                .findFirst().ifPresent(qrCodeDto->{
            channelDto.setTicket(qrCodeDto.getTicket());
            channelDto.setImageUrl(qrCodeDto.getImageUrl());
            channelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
            channelDto.setUrl(qrCodeDto.getUrl());
        }));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                doctors);
    }

    @Transactional(rollbackOn = Exception.class)
    public void addChannel(ChannelParam channelParam) {
        if (repository.existsByName(channelParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final long doctorId = idWorker.nextId();

        weChatQrCodeAppService.applyWechatQrCode(doctorId, channelParam.getType());

        final CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setUsername(channelParam.getPhone());
        createAccountCommand.setPhone(channelParam.getPhone());
        createAccountCommand.setRealName(channelParam.getName());
        createAccountCommand.setOrganizationIds(asList(1377345482606645249L));
        Long roleId = 0L;
        if (channelParam.getType().equals(ChannelType.DOCTOR)){
            roleId = 3L;
        }
        else if (channelParam.getType().equals(ChannelType.FAMILY_HOTEL)){
            roleId = 4L;
        }
        else if (channelParam.getType().equals(ChannelType.OTHER)){
            roleId = 5L;
        }
        else {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("渠道类型不正确"));
        }
        createAccountCommand.setRoleIds(asList(roleId));
        final UserDetailDto account = userAppService.createAccount(createAccountCommand);

        final Channel channel = new Channel(doctorId, account.getId(), channelParam.getName(), channelParam.getPhone(),
                channelParam.getType());
        repository.save(channel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editChannel(Long id, ChannelParam channelParam) {
        if (repository.existsByNameAndIdNot(channelParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Channel channel = requirePresent(repository.findById(id));

        channel.describe(channelParam.getName());

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
