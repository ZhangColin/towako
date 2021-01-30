package com.towako.vip.membership.domain;

import com.cartisan.domains.AggregateRoot;
import com.cartisan.domains.SoftDeleteEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author colin
 */
@Entity
@Table(name = "vip_memberships")
@Getter
@EqualsAndHashCode(callSuper = true)
@Where(clause = "active=1 and deleted=0")
public class Membership extends SoftDeleteEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "gender")
    @Convert(converter = Gender.Converter.class)
    private Gender gender;
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "country")
    private String country;
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "status")
    private Integer status;

    protected Membership() {
        this.lastLoginIp = "";
        this.status = 1;
    }

    public static Membership createByWechat(Long id, String phone, String nickname,
                                            String avatar, Gender gender, LocalDate birthday,
                                            String city, String province, String country) {
        final Membership membership = new Membership();
        membership.id = id;
        membership.phone = phone;
        membership.nickname = nickname;
        membership.avatar = avatar;
        membership.gender = gender;
        membership.birthday = birthday;
        membership.city = city;
        membership.province = province;
        membership.country = country;

        return membership;
    }

    public void recordLogin(String ip) {
        this.lastLoginIp = ip;
        this.lastLoginTime = LocalDateTime.now();
    }
}

