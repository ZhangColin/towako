package com.towako.vip.wechatmembership;

import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class WechatMembershipAppService {
    private final WechatMembershipRepository repository;

    public WechatMembershipAppService(WechatMembershipRepository repository) {
        this.repository = repository;
    }

    public String getOpenId(Long memberId){
        return repository.findByMemberId(memberId).stream().findFirst().map(WechatMembership::getOpenId).orElse("");
    }

    public String getUnionId(Long memberId){
        return repository.findByMemberId(memberId).stream().findFirst().map(WechatMembership::getUnionId).orElse("");
    }
}
