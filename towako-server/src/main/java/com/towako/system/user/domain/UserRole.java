package com.towako.system.user.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_user_roles")
@Getter
@EqualsAndHashCode
public class UserRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    private UserRole() {

    }

    public UserRole(Long roleId) {
        this.roleId = roleId;
    }
}
