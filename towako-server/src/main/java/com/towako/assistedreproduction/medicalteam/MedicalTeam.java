package com.towako.assistedreproduction.medicalteam;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ar_medical_teams")
@Getter
@EqualsAndHashCode(callSuper = true)
public class MedicalTeam extends AbstractEntity implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "medical_record_id")
    private Long medicalRecordId;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "sort")
    private Integer sort;

    private MedicalTeam() {}

    public MedicalTeam(Long medicalRecordId, Long doctorId, Integer sort) {

        this.medicalRecordId = medicalRecordId;
        this.doctorId = doctorId;
        this.sort = sort;
    }

    public void changeSort(Integer sort) {
        this.sort = sort;
    }
}
