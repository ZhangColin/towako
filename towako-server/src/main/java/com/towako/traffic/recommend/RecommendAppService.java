package com.towako.traffic.recommend;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author colin
 */
@Service
public class RecommendAppService {
    private final RecommendRepository repository;

    public RecommendAppService(RecommendRepository repository) {
        this.repository = repository;
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

}
