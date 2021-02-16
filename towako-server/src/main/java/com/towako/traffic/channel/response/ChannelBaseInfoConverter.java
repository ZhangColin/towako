package com.towako.traffic.channel.response;

import com.cartisan.dtos.Converter;
import com.towako.traffic.channel.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface ChannelBaseInfoConverter extends Converter<Channel, ChannelBaseInfoDto> {
    ChannelBaseInfoConverter CONVERTER = Mappers.getMapper(ChannelBaseInfoConverter.class);
}
