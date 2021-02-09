package com.towako.wx.mp.handler;

import com.towako.vip.membership.MembershipAppService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author colin
 */
@Component
@Slf4j
public class MpUnsubscribeHandler implements WxMpMessageHandler {
    private final MembershipAppService membershipAppService;

    public MpUnsubscribeHandler(MembershipAppService membershipAppService) {
        this.membershipAppService = membershipAppService;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        log.info("取消关注用户 OPENID: " + openId);

        final String appId = wxMpService.getWxMpConfigStorage().getAppId();
        membershipAppService.unsubscribe(appId, wxMessage.getOpenId());

        return null;
    }

}
