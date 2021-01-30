package com.towako.traffic.wechatqrcode.domain;

/**
 * @author colin
 */
public interface WeChatQrCodeProvider {
    WechatQrCodeTicket apply(String qrSceneStr);
}
