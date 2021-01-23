package com.towako.channel.familyhotel.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class FamilyHotelDto {
    @ApiModelProperty(value = "家庭旅馆Id")
    private Long id;

    @ApiModelProperty(value = "家庭旅馆名称")
    private String name;

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
