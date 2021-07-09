package com.towako.assistedreproduction.treatmentperiod;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate lastMenstrualPeriod;

    @Column(name = "bmi")
    private String bmi;

    @Column(name = "amh")
    private String amh;

    @Column(name = "plan")
    private String plan;

    @Column(name = "report")
    private String report;

    @Column(name = "report_date")
    private LocalDate reportDate;

    private TreatmentPeriod() {}

    public TreatmentPeriod(Long medicalRecordId, Integer period, LocalDate lastMenstrualPeriod, String bmi, String amh, String plan) {
        this.medicalRecordId = medicalRecordId;
        this.period = period;
        this.lastMenstrualPeriod = lastMenstrualPeriod;
        this.bmi = bmi;
        this.amh = amh;
        this.plan = plan;
    }

    public void describe(Long medicalRecordId, Integer period, LocalDate lastMenstrualPeriod, String bmi, String amh, String plan) {
        this.medicalRecordId = medicalRecordId;
        this.period = period;
        this.lastMenstrualPeriod = lastMenstrualPeriod;
        this.bmi = bmi;
        this.amh = amh;
        this.plan = plan;
    }

    public void report(String report) {
        this.report = report;
        this.reportDate = LocalDate.now();
    }
}
