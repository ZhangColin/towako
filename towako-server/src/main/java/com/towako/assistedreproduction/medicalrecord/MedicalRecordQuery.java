package com.towako.assistedreproduction.medicalrecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MedicalRecordQuery {
    @ApiModelProperty(value = "根据病案号、姓名、电话模糊查询")
    private String blurry;
}
