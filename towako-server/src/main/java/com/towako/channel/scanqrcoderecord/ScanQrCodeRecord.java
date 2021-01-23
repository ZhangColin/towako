package com.towako.channel.scanqrcoderecord;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "chl_scan_qr_code_records")
@Getter
@EqualsAndHashCode(callSuper = true)
public class ScanQrCodeRecord extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "channel_type")
    private String channelType;

    @Column(name = "qr_scene_str")
    private String qrSceneStr;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "event")
    private String event;

    private ScanQrCodeRecord() {
    }

    public ScanQrCodeRecord(Long channelId, String channelType, String qrSceneStr, String openId, String event) {
        this.channelId = channelId;
        this.channelType = channelType;
        this.qrSceneStr = qrSceneStr;
        this.openId = openId;
        this.event = event;
    }
}
