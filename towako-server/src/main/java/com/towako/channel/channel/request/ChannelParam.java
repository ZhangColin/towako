package com.towako.channel.channel.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author colin
 */
@Data
public class ChannelParam {
    @ApiModelProperty(value = "渠道名称", required = true)
    @NotBlank(message = "渠道名称不能为空")
    @Length(min = 2, max = 32, message = "渠道名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号必须11位")
    private String phone;

    @ApiModelProperty(value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String type;
}
