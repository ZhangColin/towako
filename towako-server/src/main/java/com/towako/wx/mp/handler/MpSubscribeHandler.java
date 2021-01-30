package com.towako.wx.mp.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.towako.traffic.recommend.RecommendAppService;
import com.towako.vip.membership.MembershipAppService;
import com.towako.vip.membership.response.MembershipDto;
import com.towako.vip.wechateventrecord.WeChatEventRecordAppService;
import com.towako.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * @author colin
 */
@Component
@Slf4j
public class MpSubscribeHandler implements WxMpMessageHandler {

    private final MembershipAppService membershipAppService;
    private final WxMaService wxMaService;
    private final RecommendAppService recommendAppService;
    private final WeChatEventRecordAppService weChatEventRecordAppService;

    public MpSubscribeHandler(MembershipAppService membershipAppService, WxMaService wxMaService,
                              RecommendAppService recommendAppService, WeChatEventRecordAppService weChatEventRecordAppService) {
        this.membershipAppService = membershipAppService;
        this.wxMaService = wxMaService;
        this.recommendAppService = recommendAppService;
        this.weChatEventRecordAppService = weChatEventRecordAppService;
    }

    @Override
    @Transactional
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService()
                .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                final String appId = wxMaService.getWxMaConfig().getAppid();

                String unionId = userWxInfo.getUnionId();
                if (unionId == null) {
                    // TODO: 没有开通第三方公众平台时，使用openId来替代
                    unionId=userWxInfo.getOpenId();
                }

                final Map<String, Object> allFieldsMap = wxMessage.getAllFieldsMap();
                weChatEventRecordAppService.eventRecord(userWxInfo.getOpenId(),
                        allFieldsMap.get("Event").toString(), allFieldsMap.get("EventKey").toString());

                final MembershipDto membershipDto = membershipAppService.registerByWechat(appId, userWxInfo.getOpenId(), unionId,
                        "", userWxInfo.getNickname(), userWxInfo.getHeadImgUrl(), userWxInfo.getSex(),
                        userWxInfo.getCity(), userWxInfo.getProvince(), userWxInfo.getCountry(), userWxInfo.getQrSceneStr());

                membershipAppService.recordLogin(membershipDto.getId());

                recommendAppService.recommend(allFieldsMap.get("EventKey").toString(), membershipDto.getId(), membershipDto.getNickname());
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                log.info("该公众号没有获取用户信息权限！");
            }
        }


        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {

        return null;
    }

}
