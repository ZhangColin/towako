package com.towako.channel.doctor;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "chl_doctors")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Doctor extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    private Doctor() {
    }

    public Doctor(Long id, String name) {
        this.id = id;
        this.name = name;
        this.status = 1;
    }

    public void describe(String name){
        this.name = name;
    }

    public void enable() {
        this.status = 1;
    }

    public void disable() {
        this.status = 0;
    }
}
