package com.towako.traffic.wechatqrcode;

import com.towako.traffic.wechatqrcode.domain.WeChatQrCodeProvider;
import com.towako.traffic.wechatqrcode.domain.WechatQrCode;
import com.towako.traffic.wechatqrcode.domain.WechatQrCodeTicket;
import com.towako.traffic.wechatqrcode.response.WeChatQrCodeDto;
import com.towako.traffic.wechatqrcode.response.WechatQrCodeConverter;
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

    public List<WeChatQrCodeDto> findByChannelIds(List<Long> channelIds) {
        return converter.convert(repository.findByChannelIdIn(channelIds));
    }

    @Transactional(rollbackOn = Exception.class)
    public void applyWechatQrCode(Long channelId, String channelType) {
        final String qrSceneStr = channelType + "_" + channelId;
        final WechatQrCodeTicket qrCodeTicket = qrCodeProvider.apply(qrSceneStr);

        final WechatQrCode wechatQrCode = new WechatQrCode(channelId, channelType, qrSceneStr, "",
                qrCodeTicket.getTicket(), qrCodeTicket.getExpireSeconds(), qrCodeTicket.getUrl());
        repository.save(wechatQrCode);
    }

    @Transactional(rollbackOn = Exception.class)
    public void reApplyWechatQrCode(Long channelId) {
        repository.findByChannelId(channelId).ifPresent(wechatQrCode -> {
            final String qrSceneStr = wechatQrCode.getChannelType() + "_" + wechatQrCode.getChannelId();
            final WechatQrCodeTicket qrCodeTicket = qrCodeProvider.apply(qrSceneStr);

            wechatQrCode.updateWeChatInfo(qrSceneStr, "",
                    qrCodeTicket.getTicket(), qrCodeTicket.getExpireSeconds(), qrCodeTicket.getUrl());

            repository.save(wechatQrCode);
        });


    }

}
