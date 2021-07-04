package com.towako.hospitaldoctors.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AssignHospitalsCommand {
    @ApiModelProperty(value = "分配的医院")
    private List<Long> hospitalIds;
}
