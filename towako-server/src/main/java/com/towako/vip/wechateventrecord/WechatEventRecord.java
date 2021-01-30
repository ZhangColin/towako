package com.towako.vip.wechateventrecord;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author colin
 */
@Entity
@Table(name = "vip_wechat_event_records")
@Getter
@EqualsAndHashCode(callSuper = true)
public class WechatEventRecord extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qr_scene_str")
    private String qrSceneStr;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "event")
    private String event;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    private WechatEventRecord() {
    }

    public WechatEventRecord(String openId, String event, String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
        this.openId = openId;
        this.event = event;
        this.eventDate = LocalDateTime.now();
    }
}
