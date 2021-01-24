package com.towako.vip.membership.response;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class MembershipRecommendDto {
    private Long id;
    private String recommend;
    private String channel;
}
