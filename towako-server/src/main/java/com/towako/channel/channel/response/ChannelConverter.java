package com.towako.channel.channel.response;

import com.cartisan.dtos.Converter;
import com.towako.channel.channel.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface ChannelConverter extends Converter<Channel, ChannelDto> {
    ChannelConverter CONVERTER = Mappers.getMapper(ChannelConverter.class);
}
