package com.towako.traffic.channel;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author colin
 */
@Entity
@Table(name = "tfc_channels")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Channel extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "user_id")
    @Setter
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Integer status;

    private Channel() {
    }

    public Channel(Long id, Long parentId, Long userId, String name, String phone, String type) {
        this.id = id;
        this.userId = userId;
        this.parentId = parentId;
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.status = 1;
    }

    public void describe(Long parentId, String name, String phone, String type){
        this.parentId = parentId;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public void enable() {
        this.status = 1;
    }

    public void disable() {
        this.status = 0;
    }
}
