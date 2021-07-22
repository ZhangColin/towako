package com.towako.assistedreproduction.inspectionreport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class InspectionReportParam {
    @ApiModelProperty(value = "疗程Id")
    private Long treatmentPeriodId;

    @ApiModelProperty(value = "检查日期")
    private LocalDate inspectionDate;

    @ApiModelProperty(value = "周期天数")
    private Integer cycleNumber;

    @ApiModelProperty(value = "来曲唑")
    private String letrozole;

    @ApiModelProperty(value = "HMG(IU)")
    private String hmg;

    @ApiModelProperty(value = "HCG")
    private String hcg;

    @ApiModelProperty(value = "达必佳")
    private String decapeptyl;

    @ApiModelProperty(value = "MPA(mg)")
    private String mpa;

    @ApiModelProperty(value = "CC(mg)")
    private String cc;

    @ApiModelProperty(value = "加尼瑞克/思则瑞")
    private String ganirelix;

    @ApiModelProperty(value = "芬吗通")
    private String femoston;

    @ApiModelProperty(value = "他达拉非")
    private String tadalafil;

    @ApiModelProperty(value = "内膜")
    private String intima;

    @ApiModelProperty(value = "内膜类型")
    private String intimaType;

    @ApiModelProperty(value = "LOF1")
    private String lof1;

    @ApiModelProperty(value = "LOF2")
    private String lof2;

    @ApiModelProperty(value = "LOF3")
    private String lof3;

    @ApiModelProperty(value = "LOF4")
    private String lof4;

    @ApiModelProperty(value = "LOF5")
    private String lof5;

    @ApiModelProperty(value = "LOF6")
    private String lof6;

    @ApiModelProperty(value = "ROF1")
    private String rof1;

    @ApiModelProperty(value = "ROF2")
    private String rof2;

    @ApiModelProperty(value = "ROF3")
    private String rof3;

    @ApiModelProperty(value = "ROF4")
    private String rof4;

    @ApiModelProperty(value = "ROF5")
    private String rof5;

    @ApiModelProperty(value = "ROF6")
    private String rof6;

    @ApiModelProperty(value = "FSH(mlU/ml)")
    private String fsh;

    @ApiModelProperty(value = "LH(mlU/ml)")
    private String lh;

    @ApiModelProperty(value = "E2(pg/ml)")
    private String e2;

    @ApiModelProperty(value = "T(ng/dl)")
    private String t;

    @ApiModelProperty(value = "P(ng/ml)")
    private String p;

    @ApiModelProperty(value = "PRL(ng/ml)")
    private String prl;

    @ApiModelProperty(value = "B-HCG(mlU/ml)")
    private String bhcg;

    @ApiModelProperty(value = "白带检查")
    private String leucorrhea;

    @ApiModelProperty(value = "取卵时间")
    private LocalDate takeOvumDate;

}
