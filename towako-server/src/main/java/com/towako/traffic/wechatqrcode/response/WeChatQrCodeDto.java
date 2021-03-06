package com.towako.traffic.wechatqrcode.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class WeChatQrCodeDto {
    @ApiModelProperty(value = "渠道Id")
    private Long channelId;

    @ApiModelProperty(value = "场景值")
    private String qrSceneStr;

    @ApiModelProperty(value = "二维码图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "获取二维码ticket")
    private String ticket;

    @ApiModelProperty(value = "二维码有效时间")
    private Integer expireSeconds;

    @ApiModelProperty(value = "二维码图片解析后地址")
    private String url;
}
