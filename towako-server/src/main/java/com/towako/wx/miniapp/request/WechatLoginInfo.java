package com.towako.wx.miniapp.request;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class WechatLoginInfo {
    private String code;
    private WechatUserInfo userInfo;
}
