package com.towako.channel.familyhotel.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author colin
 */
@Data
public class FamilyHotelParam {
    @ApiModelProperty(value = "家庭旅馆名称", required = true)
    @NotBlank(message = "家庭旅馆名称不能为空")
    @Length(min = 2, max = 32, message = "家庭旅馆名称必须在 2 至 32 之间")
    private String name;
}
