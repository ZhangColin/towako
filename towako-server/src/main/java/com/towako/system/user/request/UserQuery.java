package com.towako.system.user.request;

import com.cartisan.repositories.Condition;
import com.towako.system.user.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author colin
 */
@Data
public class UserQuery {
    @ApiModelProperty(value = "账号", required = true)
    @Length(min = 2, max = 32, message = "账号必须在 2 至 32 之间")
    @Condition(propName = "username", type = Condition.Type.INNER_LIKE)
    private String username;

    @ApiModelProperty(value = "手机")
    @Length(min = 1, max = 13, message = "电话长度需要在13个字以内")
    @Condition(propName = "phone", type = Condition.Type.INNER_LIKE)
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    @Condition(type = Condition.Type.INNER_LIKE)
    private String email;

    @ApiModelProperty(value = "性别")
    @Condition(propName = "gender", type = Condition.Type.EQUAL)
    private Gender gender;

    @ApiModelProperty(value = "状态")
    @Condition(propName = "status", type = Condition.Type.EQUAL)
    private Integer status;
}
