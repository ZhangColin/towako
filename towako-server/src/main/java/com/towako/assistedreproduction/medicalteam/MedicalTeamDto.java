package com.towako.assistedreproduction.medicalteam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MedicalTeamDto {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "病历Id")
    private Long medicalRecordId;

    @ApiModelProperty(value = "医生Id")
    private Long doctorId;

    @ApiModelProperty(value = "排序")
    private Integer sort = 0;

}
