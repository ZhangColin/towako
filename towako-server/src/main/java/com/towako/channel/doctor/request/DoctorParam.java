package com.towako.channel.doctor.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author colin
 */
@Data
public class DoctorParam {
    @ApiModelProperty(value = "医生名称", required = true)
    @NotBlank(message = "医生名称不能为空")
    @Length(min = 2, max = 32, message = "医生名称必须在 2 至 32 之间")
    private String name;
}
