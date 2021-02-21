package com.towako.traffic.recommend;

import com.cartisan.dtos.PageResult;
import com.towako.traffic.channel.Channel;
import com.towako.traffic.channel.ChannelAppService;
import com.towako.traffic.channel.ChannelRepository;
import com.towako.traffic.channel.request.ChannelQuery;
import com.towako.traffic.channel.response.ChannelDto;
import com.towako.traffic.recommend.response.RecommendConverter;
import com.towako.traffic.recommend.response.RecommendDto;
import com.towako.traffic.wechatqrcode.response.WeChatQrCodeDto;
import com.towako.vip.membership.MembershipRepository;
import com.towako.vip.membership.domain.Membership;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;
import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class RecommendAppService {
    private final RecommendRepository repository;
    private final RecommendConverter converter = RecommendConverter.CONVERTER;
    private final ChannelRepository channelRepository;
    private final MembershipRepository membershipRepository;

    public RecommendAppService(RecommendRepository repository, ChannelRepository channelRepository, MembershipRepository membershipRepository) {
        this.repository = repository;
        this.channelRepository = channelRepository;
        this.membershipRepository = membershipRepository;
    }

    public PageResult<RecommendDto> findByChannelId(@NonNull Long channelId, @NonNull Pageable pageable) {
        final Page<Recommend> searchResult = repository.findByChannelId(channelId,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "recommendDate")));


        final List<Membership> memberships = membershipRepository.findByIdIn(searchResult.getContent().stream().map(Recommend::getMemberId).collect(toList()));

        final List<RecommendDto> results = searchResult.getContent().stream().map(recommend -> {
            final RecommendDto dto = converter.convert(recommend);
            dto.setPhone(memberships.stream().filter(membership -> membership.getId().equals(recommend.getMemberId()))
                    .findFirst().map(Membership::getPhone).orElse(""));

            return dto;
        }).collect(toList());

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(), results);
    }

    public PageResult<RecommendDto> myRecommends(Long userId, Pageable pageable) {
        final Long channelId = requirePresent(channelRepository.findByUserId(userId), "你不是渠道用户").getId();
        return findByChannelId(channelId, pageable);
    }


    @Transactional(rollbackOn = Exception.class)
    public void recommend(String qrSceneStr, Long memberId, String nickName) {
        if (qrSceneStr.isEmpty()){
            return;
        }

        if (repository.existsByMemberId(memberId)){
            return;
        }
        //首次扫码关注是qrscene_开头
        final String[] strings = qrSceneStr.split("_");

        Long channelId = Long.parseLong(strings[strings.length-1]);

        final Recommend recommend = new Recommend(channelId, memberId, nickName);

        repository.save(recommend);
    }

    public Long getRecommendCount(Long channelId) {
        return repository.countByChannelId(channelId);
    }


}
