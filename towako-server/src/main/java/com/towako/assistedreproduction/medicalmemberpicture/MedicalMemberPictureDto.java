package com.towako.assistedreproduction.medicalmemberpicture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.lang.Long;
import java.lang.String;

@Data
public class MedicalMemberPictureDto {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "图片地址")
    private String url;

}
