package com.towako.hospitaldoctors.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DoctorParam {
    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "职称")
    private String title;

    @ApiModelProperty(value = "状态(0：禁用  1：启用 ）")
    private Integer status;

}
