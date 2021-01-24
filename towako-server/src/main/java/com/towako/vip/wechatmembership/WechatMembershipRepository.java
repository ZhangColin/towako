package com.towako.vip.repository;

import com.cartisan.repositories.BaseRepository;
import com.towako.vip.domain.WechatMembership;

import java.util.List;
import java.util.Optional;

/**
 * @author colin
 */
public interface WechatMembershipRepository extends BaseRepository<WechatMembership, Long> {
    Optional<WechatMembership> findByAppIdAndOpenId(String appId, String openId);
    List<WechatMembership> findByAppIdAndUnionId(String appId, String unionId);
}
