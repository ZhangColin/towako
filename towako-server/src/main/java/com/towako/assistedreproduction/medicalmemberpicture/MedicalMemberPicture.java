package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.lang.Long;
import java.lang.String;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ar_medical_member_pictures")
@Getter
@EqualsAndHashCode(callSuper = true)
public class MedicalMemberPicture extends AbstractEntity implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "medical_record_id")
    private Long medicalRecordId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "url")
    private String url;

    private MedicalMemberPicture() {}

    public MedicalMemberPicture(Long medicalRecordId, Long memberId, String url) {

        this.medicalRecordId = medicalRecordId;
        this.memberId = memberId;
        this.url = url;
    }

    public void describe(Long medicalRecordId, Long memberId, String url) {
        this.medicalRecordId = medicalRecordId;
        this.memberId = memberId;
        this.url = url;
    }
}
