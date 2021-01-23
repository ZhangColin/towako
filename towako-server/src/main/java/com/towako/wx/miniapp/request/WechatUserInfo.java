package com.towako.wx.miniapp.request;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class WechatUserInfo {
    private String avatarUrl;
    private String country;
    private String province;
    private String city;
    private String language;
    private Integer gender;
    private String nickName;
}
