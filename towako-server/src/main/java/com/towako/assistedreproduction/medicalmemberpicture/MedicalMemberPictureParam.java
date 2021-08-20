package com.towako.assistedreproduction.medicalmemberpicture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.lang.Long;
import java.lang.String;

@Data
public class MedicalMemberPictureParam {
    @ApiModelProperty(value = "病历Id")
    private Long medicalRecordId;

    @ApiModelProperty(value = "用户Id")
    private Long memberId;

    @ApiModelProperty(value = "图片地址")
    private String url;

}
