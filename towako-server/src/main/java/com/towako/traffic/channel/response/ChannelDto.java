package com.towako.traffic.channel.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class ChannelDto {
    @ApiModelProperty(value = "渠道Id")
    private Long id;

    @ApiModelProperty(value = "上级渠道Id")
    private Long parentId;

    @ApiModelProperty(value = "渠道名称")
    private String name;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "渠道类型")
    private String type;

    @ApiModelProperty(value = "推荐数")
    private Long recommends;

    @ApiModelProperty(value = "获取二维码ticket")
    private String ticket;

    @ApiModelProperty(value = "二维码图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "二维码有效时间")
    private Integer expireSeconds;

    @ApiModelProperty(value = "二维码图片解析后地址")
    private String url;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
