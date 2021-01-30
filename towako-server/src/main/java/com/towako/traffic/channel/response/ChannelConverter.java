package com.towako.traffic.channel.response;

import com.cartisan.dtos.Converter;
import com.towako.traffic.channel.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface ChannelConverter extends Converter<Channel, ChannelDto> {
    ChannelConverter CONVERTER = Mappers.getMapper(ChannelConverter.class);
}
