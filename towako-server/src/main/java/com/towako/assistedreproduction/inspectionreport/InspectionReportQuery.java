package com.towako.assistedreproduction.inspectionreport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InspectionReportQuery {
    @ApiModelProperty(value = "疗程Id")
    @NotNull(message = "疗程Id不能为空")
    private Long treatmentPeriodId;
}
