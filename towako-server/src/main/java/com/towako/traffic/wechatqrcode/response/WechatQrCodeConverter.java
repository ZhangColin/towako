package com.towako.traffic.wechatqrcode.response;

import com.cartisan.dtos.Converter;
import com.towako.traffic.wechatqrcode.domain.WechatQrCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface WechatQrCodeConverter extends Converter<WechatQrCode, WeChatQrCodeDto> {
    WechatQrCodeConverter CONVERTER = Mappers.getMapper(WechatQrCodeConverter.class);
}
