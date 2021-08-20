package com.towako.assistedreproduction.medicalrecord;

import com.towako.vip.membership.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AddMedicalRecordByMembership {
    @ApiModelProperty(value = "会员Id")
    private Long memberId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

}
