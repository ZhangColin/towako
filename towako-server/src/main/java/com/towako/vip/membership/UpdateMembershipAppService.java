package com.towako.vip.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.towako.vip.membership.domain.Gender;
import com.towako.vip.membership.domain.Membership;
import com.towako.vip.wechatmembership.WechatMembershipRepository;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
@Slf4j
public class UpdateMembershipAppService {
    private final MembershipRepository membershipRepository;
    private final WechatMembershipRepository wechatMembershipRepository;
    private final WxMpService weixinService;
    private final ObjectMapper objectMapper;

    public UpdateMembershipAppService(MembershipRepository membershipRepository, WechatMembershipRepository wechatMembershipRepository,
                                      WxMpService weixinService, ObjectMapper objectMapper) {
        this.membershipRepository = membershipRepository;
        this.wechatMembershipRepository = wechatMembershipRepository;
        this.weixinService = weixinService;
        this.objectMapper = objectMapper;
    }


    public void updateWechatInfo() {
        wechatMembershipRepository.findAll().forEach(wechatMembership -> {
            if(!membershipRepository.findById(wechatMembership.getMemberId()).isPresent()){
                try {
                    WxMpUser userWxInfo = weixinService.getUserService()
                            .userInfo(wechatMembership.getOpenId(), null);
                    log.info(objectMapper.writeValueAsString(userWxInfo));
                    Gender gender = userWxInfo.getSex()==null?Gender.UNKNOWN:Gender.getInstance(userWxInfo.getSex());
                    final Membership membership = Membership.createByWechat(wechatMembership.getMemberId(), "",
                            userWxInfo.getNickname(), userWxInfo.getHeadImgUrl(),
                            gender, null,
                            userWxInfo.getCity(), userWxInfo.getProvince(), userWxInfo.getCountry());

                    membershipRepository.save(membership);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        });
    }
}
