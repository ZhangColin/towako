package com.towako.traffic.channel.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class ChannelBaseInfoDto {
    @ApiModelProperty(value = "渠道Id")
    private Long id;

    @ApiModelProperty(value = "上级渠道Id")
    private Long parentId;

    @ApiModelProperty(value = "渠道名称")
    private String name;
}
