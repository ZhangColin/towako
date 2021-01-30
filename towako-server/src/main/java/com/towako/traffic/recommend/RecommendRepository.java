package com.towako.traffic.recommend;

import com.cartisan.repositories.BaseRepository;

public interface RecommendRepository extends BaseRepository<Recommend, Long> {
    boolean existsByMemberId(Long memberId);
}
