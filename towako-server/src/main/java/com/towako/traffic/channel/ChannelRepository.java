package com.towako.traffic.channel;

import com.cartisan.repositories.BaseRepository;

import java.util.Optional;

public interface ChannelRepository extends BaseRepository<Channel, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    Optional<Channel> findByUserId(Long userId);
}
