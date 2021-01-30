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

    public RecommendAppService(RecommendRepository repository, ChannelRepository channelRepository) {
        this.repository = repository;
        this.channelRepository = channelRepository;
    }

    public PageResult<RecommendDto> findByChannelId(@NonNull Long channelId, @NonNull Pageable pageable) {
        final Page<Recommend> searchResult = repository.findByChannelId(channelId,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "recommendDate")));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public PageResult<RecommendDto> myRecommends(Long userId, Pageable pageable) {
        final Long channelId = requirePresent(channelRepository.findByUserId(userId), "你不是渠道用户").getId();
        return findByChannelId(channelId, pageable);
    }


    @Transactional(rollbackOn = Exception.class)
    public void recommend(String qrSceneStr, Long memberId, String nickName) {
        if (repository.existsByMemberId(memberId)){
            return;
        }
        //首次扫码关注是qrscene_开头
        final String[] strings = qrSceneStr.split("_");
        Long channelId = Long.parseLong(strings[2]);
        String channelType = strings[1];

        final Recommend recommend = new Recommend(channelId, channelType, memberId, nickName);

        repository.save(recommend);
    }

    public Long getRecommendCount(Long channelId) {
        return repository.countByChannelId(channelId);
    }


}
