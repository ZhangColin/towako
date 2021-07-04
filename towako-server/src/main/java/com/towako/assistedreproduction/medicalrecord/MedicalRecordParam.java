package com.towako.assistedreproduction.medicalrecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MedicalRecordParam {
    @ApiModelProperty(value = "病案号")
    private String recordNo;

    @ApiModelProperty(value = "IVF(AIH)号")
    private String ivf;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "主诉")
    private String mainAppeal;

    @ApiModelProperty(value = "现病史")
    private String hpi;

    @ApiModelProperty(value = "既往史")
    private String medicalHistory;

    @ApiModelProperty(value = "男方")
    private String man;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "婚姻状况")
    private Integer maritalStatus;

}
