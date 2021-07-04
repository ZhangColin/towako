package com.towako.hospitaldoctors.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DoctorParam {
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "职称")
    private String title;

}
