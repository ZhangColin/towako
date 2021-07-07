package com.towako.assistedreproduction.treatmentperiod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TreatmentPeriodQuery {
    @ApiModelProperty(value = "病历Id")
    @NotNull(message = "病历Id不能为空")
    private Long medicalRecordId;
}
