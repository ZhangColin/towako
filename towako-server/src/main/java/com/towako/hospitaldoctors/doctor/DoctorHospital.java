package com.towako.hospitaldoctors.doctor;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "hd_doctor_hospitals")
@Getter
@EqualsAndHashCode
public class DoctorHospital {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hospital_id")
    private Long hospitalId;

    private DoctorHospital() {
    }

    public DoctorHospital(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
}
