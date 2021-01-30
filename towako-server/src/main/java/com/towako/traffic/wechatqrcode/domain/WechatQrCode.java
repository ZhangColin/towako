package com.towako.traffic.wechatqrcode.domain;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "tfc_wechat_qr_codes")
@Getter
@EqualsAndHashCode(callSuper = true)
public class WechatQrCode extends AbstractEntity implements AggregateRoot {
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

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "expire_seconds")
    private Integer expireSeconds;

    @Column(name = "url")
    private String url;

    private WechatQrCode() {
    }

    public WechatQrCode(Long channelId, String channelType, String qrSceneStr, String imageUrl,
                        String ticket, Integer expireSeconds, String url) {
        this.channelId = channelId;
        this.channelType = channelType;
        this.qrSceneStr = qrSceneStr;
        this.imageUrl = imageUrl;
        this.ticket = ticket;
        this.expireSeconds = expireSeconds;
        this.url = url;
    }

    public void updateWeChatInfo(String qrSceneStr, String imageUrl,
                                 String ticket, Integer expireSeconds, String url) {
        this.qrSceneStr = qrSceneStr;
        this.imageUrl = imageUrl;
        this.ticket = ticket;
        this.expireSeconds = expireSeconds;
        this.url = url;
    }
}
