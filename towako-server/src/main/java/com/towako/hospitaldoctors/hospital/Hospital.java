package com.towako.hospitaldoctors.hospital;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "hd_hospitals")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Hospital extends AbstractEntity implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    private Hospital() {}

    public Hospital(String name, String description) {

        this.name = name;
        this.description = description;
    }

    public void describe(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
