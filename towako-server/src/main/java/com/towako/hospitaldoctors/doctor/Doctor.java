package com.towako.hospitaldoctors.doctor;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import com.towako.system.role.domain.RoleMenu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "hd_doctors")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Doctor extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "doctor_id")
    private List<DoctorHospital> hospitals = new ArrayList<>();

    private Doctor() {}

    public Doctor(Long id, Long userId, String name, String phone, String title) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.status = 1;
    }

    public void describe(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public void assignHospitals(List<Long> hospitalIds) {
        this.hospitals.removeAll(this.hospitals.stream()
                .filter(doctorHospital->!hospitalIds.contains(doctorHospital.getHospitalId()))
                .collect(toList()));

        hospitalIds.removeAll(this.hospitals.stream().map(DoctorHospital::getHospitalId).collect(toList()));
        hospitalIds.forEach(hospitalId->this.hospitals.add(new DoctorHospital(hospitalId)));
    }
}
