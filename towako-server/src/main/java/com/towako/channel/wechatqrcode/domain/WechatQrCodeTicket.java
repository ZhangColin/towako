package com.towako.channel.wechatqrcode.domain;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class WechatQrCodeTicket {
    private String ticket;
    private int expireSeconds = -1;
    private String url;
}
