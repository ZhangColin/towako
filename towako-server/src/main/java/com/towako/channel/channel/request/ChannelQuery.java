package com.towako.channel.channel.request;

import com.cartisan.repositories.Condition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class ChannelQuery {
    @ApiModelProperty(value = "渠道名称", required = true)
    @Condition(propName = "name", type = Condition.Type.INNER_LIKE)
    private String name;

    @ApiModelProperty(value = "渠道类型", required = true)
    @Condition(propName = "type", type = Condition.Type.EQUAL)
    private String type;

    @ApiModelProperty(value = "状态")
    @Condition(propName = "status", type = Condition.Type.EQUAL)
    private Integer status;
}
