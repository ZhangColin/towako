package com.towako.hospitaldoctors.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author colin
 */
@Data
public class RegisterDoctorCommand {
    @ApiModelProperty(value = "医院Id", required = true)
    private Long hospitalId;

    @ApiModelProperty(value = "医生姓名", required = true)
    @NotBlank(message = "医生姓名不能为空")
    @Length(min = 2, max = 32, message = "渠道名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "职称", required = true)
    @NotBlank(message = "职称不能为空")
    @Length(min = 2, max = 32, message = "职称必须在 2 至 32 之间")
    private String title;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号必须11位")
    private String phone;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}
