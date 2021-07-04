package com.towako.assistedreproduction.treatmentperiod;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ar_treatment_periods")
@Getter
@EqualsAndHashCode(callSuper = true)
public class TreatmentPeriod extends AbstractEntity implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "medical_record_id")
    private Long medicalRecordId;

    @Column(name = "period")
    private Integer period;

    @Column(name = "last_menstrual_period")
    private Date lastMenstrualPeriod;

    @Column(name = "bmi")
    private String bmi;

    @Column(name = "amh")
    private String amh;

    @Column(name = "plan")
    private String plan;

    @Column(name = "report")
    private String report;

    @Column(name = "report_date")
    private Date reportDate;

    private TreatmentPeriod() {}

    public TreatmentPeriod(Long medicalRecordId, Integer period, Date lastMenstrualPeriod, String bmi, String amh, String plan, String report, Date reportDate) {

        this.medicalRecordId = medicalRecordId;
        this.period = period;
        this.lastMenstrualPeriod = lastMenstrualPeriod;
        this.bmi = bmi;
        this.amh = amh;
        this.plan = plan;
        this.report = report;
        this.reportDate = reportDate;
    }

    public void describe(Long medicalRecordId, Integer period, Date lastMenstrualPeriod, String bmi, String amh, String plan, String report, Date reportDate) {
        this.medicalRecordId = medicalRecordId;
        this.period = period;
        this.lastMenstrualPeriod = lastMenstrualPeriod;
        this.bmi = bmi;
        this.amh = amh;
        this.plan = plan;
        this.report = report;
        this.reportDate = reportDate;
    }
}
