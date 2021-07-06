package com.towako.assistedreproduction.inspectionreport;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ar_inspection_reports")
@Getter
@EqualsAndHashCode(callSuper = true)
public class InspectionReport extends AbstractEntity implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "treatment_period_id")
    private Long treatmentPeriodId;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "inspection_date")
    private Date inspectionDate;

    @Column(name = "cycle_number")
    private Integer cycleNumber;

    @Column(name = "letrozole")
    private String letrozole;

    @Column(name = "hmg")
    private String hmg;

    @Column(name = "mpa")
    private String mpa;

    @Column(name = "cc")
    private String cc;

    @Column(name = "ganirelix")
    private String ganirelix;

    @Column(name = "femoston")
    private String femoston;

    @Column(name = "tadalafil")
    private String tadalafil;

    @Column(name = "intima")
    private String intima;

    @Column(name = "intima_type")
    private String intimaType;

    @Column(name = "lof1")
    private String lof1;

    @Column(name = "lof2")
    private String lof2;

    @Column(name = "lof3")
    private String lof3;

    @Column(name = "lof4")
    private String lof4;

    @Column(name = "lof5")
    private String lof5;

    @Column(name = "lof6")
    private String lof6;

    @Column(name = "rof1")
    private String rof1;

    @Column(name = "rof2")
    private String rof2;

    @Column(name = "rof3")
    private String rof3;

    @Column(name = "rof4")
    private String rof4;

    @Column(name = "rof5")
    private String rof5;

    @Column(name = "rof6")
    private String rof6;

    @Column(name = "fsh")
    private String fsh;

    @Column(name = "lh")
    private String lh;

    @Column(name = "e2")
    private String e2;

    @Column(name = "t")
    private String t;

    @Column(name = "p")
    private String p;

    @Column(name = "prl")
    private String prl;

    @Column(name = "bhcg")
    private String bhcg;

    @Column(name = "leucorrhea")
    private String leucorrhea;

    private InspectionReport() {}

    public InspectionReport(Long treatmentPeriodId, Date inspectionDate, Integer cycleNumber, String letrozole, String hmg, String mpa, String cc, String ganirelix, String femoston, String tadalafil, String intima, String intimaType, String lof1, String lof2, String lof3, String lof4, String lof5, String lof6, String rof1, String rof2, String rof3, String rof4, String rof5, String rof6, String fsh, String lh, String e2, String t, String p, String prl, String bhcg, String leucorrhea) {

        this.treatmentPeriodId = treatmentPeriodId;
        this.inspectionDate = inspectionDate;
        this.cycleNumber = cycleNumber;
        this.letrozole = letrozole;
        this.hmg = hmg;
        this.mpa = mpa;
        this.cc = cc;
        this.ganirelix = ganirelix;
        this.femoston = femoston;
        this.tadalafil = tadalafil;
        this.intima = intima;
        this.intimaType = intimaType;
        this.lof1 = lof1;
        this.lof2 = lof2;
        this.lof3 = lof3;
        this.lof4 = lof4;
        this.lof5 = lof5;
        this.lof6 = lof6;
        this.rof1 = rof1;
        this.rof2 = rof2;
        this.rof3 = rof3;
        this.rof4 = rof4;
        this.rof5 = rof5;
        this.rof6 = rof6;
        this.fsh = fsh;
        this.lh = lh;
        this.e2 = e2;
        this.t = t;
        this.p = p;
        this.prl = prl;
        this.bhcg = bhcg;
        this.leucorrhea = leucorrhea;
    }

    public void describe(Long treatmentPeriodId, Date inspectionDate, Integer cycleNumber, String letrozole, String hmg, String mpa, String cc, String ganirelix, String femoston, String tadalafil, String intima, String intimaType, String lof1, String lof2, String lof3, String lof4, String lof5, String lof6, String rof1, String rof2, String rof3, String rof4, String rof5, String rof6, String fsh, String lh, String e2, String t, String p, String prl, String bhcg, String leucorrhea) {
        this.treatmentPeriodId = treatmentPeriodId;
        this.inspectionDate = inspectionDate;
        this.cycleNumber = cycleNumber;
        this.letrozole = letrozole;
        this.hmg = hmg;
        this.mpa = mpa;
        this.cc = cc;
        this.ganirelix = ganirelix;
        this.femoston = femoston;
        this.tadalafil = tadalafil;
        this.intima = intima;
        this.intimaType = intimaType;
        this.lof1 = lof1;
        this.lof2 = lof2;
        this.lof3 = lof3;
        this.lof4 = lof4;
        this.lof5 = lof5;
        this.lof6 = lof6;
        this.rof1 = rof1;
        this.rof2 = rof2;
        this.rof3 = rof3;
        this.rof4 = rof4;
        this.rof5 = rof5;
        this.rof6 = rof6;
        this.fsh = fsh;
        this.lh = lh;
        this.e2 = e2;
        this.t = t;
        this.p = p;
        this.prl = prl;
        this.bhcg = bhcg;
        this.leucorrhea = leucorrhea;
    }
}
