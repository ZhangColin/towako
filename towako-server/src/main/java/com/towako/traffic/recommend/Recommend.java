package com.towako.traffic.recommend;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author colin
 */
@Entity
@Table(name = "tfc_recommends")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Recommend extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "member_id")
    @Setter
    private Long memberId;

    @Column(name = "nickname")
    @Setter
    private String nickName;

    @Column(name = "recommend_date")
    private LocalDateTime recommendDate;

    private Recommend() {
    }

    public Recommend(Long channelId, Long memberId, String nickName) {
        this.channelId = channelId;
        this.memberId = memberId;
        this.nickName = nickName;

        this.recommendDate = LocalDateTime.now();
    }
}
