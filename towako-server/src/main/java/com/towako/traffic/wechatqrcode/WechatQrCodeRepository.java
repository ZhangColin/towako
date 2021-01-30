package com.towako.traffic.wechatqrcode;

import com.cartisan.repositories.BaseRepository;
import com.towako.traffic.wechatqrcode.domain.WechatQrCode;

import java.util.List;
import java.util.Optional;

public interface WechatQrCodeRepository extends BaseRepository<WechatQrCode, Long> {
    List<WechatQrCode> findByChannelIdIn(List<Long> channelIds);

    Optional<WechatQrCode> findByChannelId(Long channelId);
}
