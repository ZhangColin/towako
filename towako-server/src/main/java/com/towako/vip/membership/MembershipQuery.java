package com.towako.vip.membership;

import com.cartisan.repositories.Condition;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Date;

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

    @ApiModelProperty(value = "开始注册时间")
    @Condition(propName = "createDateTime", type = Condition.Type.GREATER_EQUAL)
    private LocalDateTime startDate;

    @ApiModelProperty(value = "结束注册时间")
    @Condition(propName = "createDateTime", type = Condition.Type.LESS_EQUAL)
    private LocalDateTime endDate;

    @ApiModelProperty(value = "手机")
    @Condition(propName = "phone", type = Condition.Type.EQUAL)
    private String phone;
}
