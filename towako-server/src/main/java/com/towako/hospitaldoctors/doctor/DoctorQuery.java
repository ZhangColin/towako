package com.towako.hospitaldoctors.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DoctorQuery {
    @ApiModelProperty(value = "医院")
    private Long hospitalId;
}
