package com.towako.assistedreproduction.treatmentperiod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TreatmentPeriodDto {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "病历Id")
    private Long medicalRecordId;

    @ApiModelProperty(value = "第几疗程")
    private Integer period;

    @ApiModelProperty(value = "末次月经")
    private Date lastMenstrualPeriod;

    @ApiModelProperty(value = "BMI")
    private String bmi;

    @ApiModelProperty(value = "AMH")
    private String amh;

    @ApiModelProperty(value = "方案")
    private String plan;

    @ApiModelProperty(value = "诊断报告")
    private String report;

    @ApiModelProperty(value = "报告日期")
    private Date reportDate;

}
