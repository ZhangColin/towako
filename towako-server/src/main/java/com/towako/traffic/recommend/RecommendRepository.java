package com.towako.traffic.recommend;

import com.cartisan.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecommendRepository extends BaseRepository<Recommend, Long> {
    boolean existsByMemberId(Long memberId);
    Recommend findByMemberId(Long memberId);

    Long countByChannelId(Long channelId);

    Page<Recommend> findByChannelId(Long channelId, Pageable pageable);
}
