package com.towako.hospitaldoctors.doctor;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    private Doctor() {}

    public Doctor(Long id, Long userId, String name, String phone, String title, Integer status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.status = status;
    }

    public void describe(Long userId, String name, String phone, String title, Integer status) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.status = status;
    }
}
