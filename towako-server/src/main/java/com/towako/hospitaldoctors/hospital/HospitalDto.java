package com.towako.hospitaldoctors.hospital;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HospitalDto {
    @ApiModelProperty(value = "医院Id")
    private Long id;

    @ApiModelProperty(value = "医院名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

}
