package com.towako.traffic.channel;

import com.cartisan.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends BaseRepository<Channel, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    List<Channel> findByStatus(Integer status);

    Optional<Channel> findByUserId(Long userId);
}
