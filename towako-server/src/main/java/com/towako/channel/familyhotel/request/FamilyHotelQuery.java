package com.towako.channel.familyhotel.request;

import com.cartisan.repositories.Condition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author colin
 */
@Data
public class FamilyHotelQuery {
    @ApiModelProperty(value = "家庭旅馆名称", required = true)
    @Length(min = 2, max = 32, message = "家庭旅馆名称必须在 2 至 32 之间")
    @Condition(propName = "name", type = Condition.Type.INNER_LIKE)
    private String name;

    @ApiModelProperty(value = "状态")
    @Condition(propName = "status", type = Condition.Type.EQUAL)
    private Integer status;
}
