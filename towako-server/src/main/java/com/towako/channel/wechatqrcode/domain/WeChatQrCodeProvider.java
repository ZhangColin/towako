package com.towako.channel.wechatqrcode.domain;

/**
 * @author colin
 */
public interface WeChatQrCodeProvider {
    WechatQrCodeTicket apply(String qrSceneStr);
}
