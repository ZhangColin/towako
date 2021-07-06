package com.towako.assistedreproduction.medicalrecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MedicalRecordDto {
    @ApiModelProperty(value = "病历Id")
    private Long id;

    @ApiModelProperty(value = "病案号")
    private String recordNo;

    @ApiModelProperty(value = "IVF(AIH)号")
    private String ivf;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "医院Id")
    private Long hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
}
