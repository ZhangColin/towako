package com.towako.wx.mp.handler;

import com.towako.traffic.channel.Channel;
import com.towako.traffic.channel.ChannelAppService;
import com.towako.traffic.channel.ChannelRepository;
import com.towako.traffic.channel.ChannelType;
import com.towako.traffic.channel.response.ChannelDto;
import com.towako.traffic.recommend.RecommendAppService;
import com.towako.vip.membership.MembershipAppService;
import com.towako.vip.membership.response.MembershipDto;
import com.towako.vip.wechateventrecord.WechatEventRecordAppService;
import com.towako.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

/**
 * @author colin
 */
@Component
@Slf4j
public class MpSubscribeHandler implements WxMpMessageHandler {

    private final MembershipAppService membershipAppService;
    private final RecommendAppService recommendAppService;
    private final ChannelRepository channelRepository;
    private final WechatEventRecordAppService weChatEventRecordAppService;

    public MpSubscribeHandler(MembershipAppService membershipAppService,
                              RecommendAppService recommendAppService,
                              ChannelRepository channelRepository,
                              WechatEventRecordAppService weChatEventRecordAppService) {
        this.membershipAppService = membershipAppService;
        this.recommendAppService = recommendAppService;
        this.channelRepository = channelRepository;
        this.weChatEventRecordAppService = weChatEventRecordAppService;
    }

    @Override
    @Transactional
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = wxMpService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                final String appId = wxMpService.getWxMpConfigStorage().getAppId();

                String unionId = userWxInfo.getUnionId();
                if (unionId == null) {
                    // 没有开通第三方公众平台时，使用openId来替代
                    unionId = userWxInfo.getOpenId();
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
            final Map<String, Object> allFieldsMap = wxMessage.getAllFieldsMap();
            final String qrSceneStr = allFieldsMap.get("EventKey").toString();

            if (!qrSceneStr.isEmpty()) {
                //首次扫码关注是qrscene_开头
                final String[] strings = qrSceneStr.split("_");

                Long channelId = Long.parseLong(strings[strings.length - 1]);
                final Optional<Channel> channelOptional = channelRepository.findById(channelId);

                if (channelOptional.isPresent() && channelOptional.get().getType().equals(ChannelType.DOCTOR)) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UECJU6fYRm6ga669ODito5c").build());
                    return null;
                }
            }
            return replyTextMenu(wxMessage, wxMpService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private WxMpXmlOutMessage replyTextMenu(WxMpXmlMessage wxMessage, WxMpService wxMpService) {
        String content = "感谢关注优生慧公众平台，在这里我们将为诸多备孕家庭提供一系列的国内外医生大咖助孕宝典、就医指南以及优生通道。平台将不定期发放患者福利，敬请期待！\n\n"
                + "回复数字了解详情：\n"
                + "1. 上海方法介绍\n"
                + "2. 永远幸初诊指南\n"
                + "3. 九院初诊指南\n"
                + "4. 匡延平主任科普园地\n"
                + "5. 永远幸介绍\n"
                + "6. 永远幸实验室介绍\n"
                + "7. 宫腔镜手术介绍及预约流程\n"
                + "8. 在线预约挂号\n"
                + "9. 专家亲诊\n\n"
                + "名医助孕，智慧优生！\n";
        return new TextBuilder().build(content, wxMessage, wxMpService);
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {

        return null;
    }

}
