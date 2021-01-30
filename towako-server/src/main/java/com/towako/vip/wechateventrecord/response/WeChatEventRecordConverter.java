package com.towako.vip.wechateventrecord.response;

import com.cartisan.dtos.Converter;
import com.towako.vip.wechateventrecord.WechatEventRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface WeChatEventRecordConverter extends Converter<WechatEventRecord, WeChatEventRecordDto> {
    WeChatEventRecordConverter CONVERTER = Mappers.getMapper(WeChatEventRecordConverter.class);
}
