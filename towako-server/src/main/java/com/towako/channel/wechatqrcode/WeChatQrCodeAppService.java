package com.towako.channel.wechatqrcode;

import com.towako.channel.doctor.response.DoctorConverter;
import com.towako.channel.wechatqrcode.domain.WeChatQrCodeProvider;
import com.towako.channel.wechatqrcode.domain.WechatQrCode;
import com.towako.channel.wechatqrcode.domain.WechatQrCodeTicket;
import com.towako.channel.wechatqrcode.response.WeChatQrCodeDto;
import com.towako.channel.wechatqrcode.response.WechatQrCodeConverter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author colin
 */
@Service
public class WeChatQrCodeAppService {
    private final WechatQrCodeRepository repository;
    private final WeChatQrCodeProvider qrCodeProvider;

    private final WechatQrCodeConverter converter = WechatQrCodeConverter.CONVERTER;


    public WeChatQrCodeAppService(WechatQrCodeRepository repository, WeChatQrCodeProvider qrCodeProvider) {
        this.repository = repository;
        this.qrCodeProvider = qrCodeProvider;
    }

    public List<WeChatQrCodeDto> findByChannelIds(List<Long> channelIds, String channelType) {
        return converter.convert(repository.findByChannelIdInAndChannelType(channelIds, channelType));
    }

    @Transactional(rollbackOn = Exception.class)
    public void applyWechatQrCode(Long channelId, String channelType) {
        final String qrSceneStr = channelType + "_" + channelId;
        final WechatQrCodeTicket qrCodeTicket = qrCodeProvider.apply(qrSceneStr);

        final WechatQrCode wechatQrCode = new WechatQrCode(channelId, channelType, qrSceneStr, "",
                qrCodeTicket.getTicket(), qrCodeTicket.getExpireSeconds(), qrCodeTicket.getUrl());
        repository.save(wechatQrCode);
    }

}
