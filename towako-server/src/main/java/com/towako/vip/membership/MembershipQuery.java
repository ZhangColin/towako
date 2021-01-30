package com.towako.vip.membership;

import com.cartisan.repositories.Condition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author colin
 */
@Data
public class MembershipQuery {
    @ApiModelProperty(value = "会员名称", required = true)
    @Length(min = 2, max = 32, message = "会员名称必须在 2 至 32 之间")
    @Condition(propName = "nickname", type = Condition.Type.INNER_LIKE)
    private String nickname;

    @ApiModelProperty(value = "状态")
    @Condition(propName = "status", type = Condition.Type.EQUAL)
    private Integer status;
}
