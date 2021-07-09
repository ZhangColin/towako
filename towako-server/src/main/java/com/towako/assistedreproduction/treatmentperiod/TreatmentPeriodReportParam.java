package com.towako.assistedreproduction.treatmentperiod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TreatmentPeriodReportParam {
    @ApiModelProperty(value = "诊断报告")
    private String report;

}
