package com.towako.assistedreproduction.treatmentperiod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TreatmentPeriodParam {
    @ApiModelProperty(value = "病历Id")
    private Long medicalRecordId;

    @ApiModelProperty(value = "第几疗程")
    private Integer period;

    @ApiModelProperty(value = "末次月经")
    private LocalDate lastMenstrualPeriod;

    @ApiModelProperty(value = "BMI")
    private String bmi;

    @ApiModelProperty(value = "AMH")
    private String amh;

    @ApiModelProperty(value = "方案")
    private String plan;

}
