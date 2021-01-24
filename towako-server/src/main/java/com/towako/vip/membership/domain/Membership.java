package com.towako.vip.membership.domain;

import com.cartisan.domains.AggregateRoot;
import com.cartisan.domains.SoftDeleteEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate lastLoginTime;
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
        final Membership user = new Membership();
        user.id = id;
        user.phone = phone;
        user.nickname = nickname;
        user.avatar = avatar;
        user.gender = gender;
        user.birthday = birthday;
        user.city = city;
        user.province = province;
        user.country = country;

        return user;
    }

    public void recordLogin(String ip) {
        this.lastLoginIp = ip;
        this.lastLoginTime = LocalDate.now();
    }
}

