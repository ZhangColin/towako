package com.towako.channel.channel;

import com.cartisan.repositories.BaseRepository;

public interface ChannelRepository extends BaseRepository<Channel, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
