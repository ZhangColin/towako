package com.towako.channel.wechatqrcode.gateway;

import com.towako.channel.wechatqrcode.domain.WeChatQrCodeProvider;
import com.towako.channel.wechatqrcode.domain.WechatQrCodeTicket;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Component;

/**
 * @author colin
 */
@Component
@Slf4j
public class WeChatQrCodeProviderImpl implements WeChatQrCodeProvider {
    private final WxMpService wxService;

    public WeChatQrCodeProviderImpl(WxMpService wxService) {
        this.wxService = wxService;
    }

    @Override
    public WechatQrCodeTicket apply(String qrSceneStr) {
        final WxMpQrCodeTicket wxMpQrCodeTicket;
        try {
            wxMpQrCodeTicket = this.wxService.getQrcodeService().qrCodeCreateLastTicket(qrSceneStr);

            final WechatQrCodeTicket wechatQrCodeTicket = new WechatQrCodeTicket();
            wechatQrCodeTicket.setTicket(wxMpQrCodeTicket.getTicket());
            wechatQrCodeTicket.setExpireSeconds(wxMpQrCodeTicket.getExpireSeconds());
            wechatQrCodeTicket.setUrl(wxMpQrCodeTicket.getUrl());

            return wechatQrCodeTicket;
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            return new WechatQrCodeTicket();
        }
    }
}
