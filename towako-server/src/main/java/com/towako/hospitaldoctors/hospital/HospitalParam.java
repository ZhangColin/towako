package com.towako.hospitaldoctors.hospital;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HospitalParam {
    @ApiModelProperty(value = "医院名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

}
