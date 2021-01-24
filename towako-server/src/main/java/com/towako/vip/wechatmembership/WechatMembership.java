package com.towako.vip.wechatmembership;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author colin
 */
@Entity
@Table(name = "vip_wechat_memberships")
@Getter
@EqualsAndHashCode(callSuper = true)
public class WechatMembership extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "open_id")
    private String openId;
    @Column(name = "union_id")
    private String unionId;
    @Column(name = "subscribe_time")
    private LocalDate subscribeTime;
    @Column(name = "subscribe")
    private boolean isSubscribe;
    @Column(name = "subscribe_scene")
    private String subscribeScene;
    @Column(name = "qr_scene_str")
    private String qrSceneStr;
    @Column(name = "remark")
    private String remark;
    @Column(name = "source")
    private String source;

    protected WechatMembership() {

    }

    public WechatMembership(Long memberId, String appId, String openId, String unionId, String qrSceneStr, String source) {
        this.memberId = memberId;
        this.appId = appId;
        this.openId = openId;
        this.unionId = unionId;
        this.source = source;
        this.qrSceneStr = qrSceneStr;

        this.isSubscribe = false;
        this.subscribeScene = "";
        this.remark = "";
    }

    public void subscribe() {
        this.subscribeTime = LocalDate.now();
        this.isSubscribe = true;
    }

    public void unSubscribe() {
        this.isSubscribe = false;
    }
}

