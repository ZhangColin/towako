package com.towako.assistedreproduction.medicalrecord;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ar_medical_records")
@Getter
@EqualsAndHashCode(callSuper = true)
public class MedicalRecord extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "record_no")
    private String recordNo;

    @Column(name = "ivf")
    private String ivf;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "age")
    private Integer age;

    @Column(name = "main_appeal")
    private String mainAppeal;

    @Column(name = "hpi")
    private String hpi;

    @Column(name = "medical_history")
    private String medicalHistory;

    @Column(name = "man")
    private String man;

    @Column(name = "nation")
    private String nation;

    @Column(name = "marital_status")
    private Integer maritalStatus;

    private MedicalRecord() {}

    public MedicalRecord(Long id, Long hospitalId, String recordNo, String ivf, String name, String phone, String idCard, Integer age, String mainAppeal, String hpi, String medicalHistory, String man, String nation, Integer maritalStatus) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.recordNo = recordNo;
        this.ivf = ivf;
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.age = age;
        this.mainAppeal = mainAppeal;
        this.hpi = hpi;
        this.medicalHistory = medicalHistory;
        this.man = man;
        this.nation = nation;
        this.maritalStatus = maritalStatus;
    }

    public void describe(String recordNo, String ivf, String name, String phone, String idCard, Integer age, String mainAppeal, String hpi, String medicalHistory, String man, String nation, Integer maritalStatus) {
        this.recordNo = recordNo;
        this.ivf = ivf;
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.age = age;
        this.mainAppeal = mainAppeal;
        this.hpi = hpi;
        this.medicalHistory = medicalHistory;
        this.man = man;
        this.nation = nation;
        this.maritalStatus = maritalStatus;
    }
}
