package com.towako.system.resource.domain;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_resource_categories")
@Getter
@EqualsAndHashCode(callSuper = true)
public class ResourceCategory extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sort")
    private Integer sort;

    private ResourceCategory() {
    }

    public ResourceCategory(String name, Integer sort) {
        this.name = name;
        this.sort = sort;
    }

    public void describe(String name, Integer sort){
        this.name = name;
        this.sort = sort;
    }
}
