package com.towako.channel.wechatqrcode;

import com.cartisan.repositories.BaseRepository;
import com.towako.channel.wechatqrcode.domain.WechatQrCode;

import java.util.List;

public interface WechatQrCodeRepository extends BaseRepository<WechatQrCode, Long> {
    List<WechatQrCode> findByChannelIdIn(List<Long> channelIds);
}
