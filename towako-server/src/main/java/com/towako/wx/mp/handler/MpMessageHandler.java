package com.towako.wx.mp.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.towako.vip.membership.MembershipAppService;
import com.towako.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Arrays.asList;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author colin
 */
@Component
@Slf4j
public class MpMessageHandler implements WxMpMessageHandler {
//    private final ObjectMapper objectMapper;

    public MpMessageHandler(MembershipAppService membershipAppService) {

    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }
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

        try {

            if (wxMessage.getContent().equals("1")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UFw-KP1-Q0eyhqIU9Pq4WtM").build());
                return null;
            } else if (wxMessage.getContent().equals("2")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UBD29owIN8jLbfjZ4V6jPqI").build());
                return null;
            } else if (wxMessage.getContent().equals("3") || wxMessage.getContent().equals("九院")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UNNx-eEnSvQm1ZT-fVTlbZA").build());
                return null;
            } else if (wxMessage.getContent().equals("4") || wxMessage.getContent().equals("匡延平")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UI2N5-9a0eas9Sx8sfGQGAQ").build());
                return null;
            } else if (wxMessage.getContent().equals("5") || wxMessage.getContent().equals("永远幸")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UACBsKFcdXE4-PEH61EGzJ0").build());
                return null;
            } else if (wxMessage.getContent().equals("6")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UCaP4AM-jkhI-skWHTAhaXQ").build());
                return null;
            } else if (wxMessage.getContent().equals("7") || wxMessage.getContent().equals("宫腔镜")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UACIwkVOwhe0id5KJGBSSPw").build());
                return null;
            } else if (wxMessage.getContent().equals("8") || wxMessage.getContent().equals("预约")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MINIPROGRAMPAGE().toUser(wxMessage.getFromUser()).appId("wx48291232e4e2b753").title("名医助孕，智慧优生！").pagePath("pages/reserve/quick/quick?deptId=92296185").thumbMediaId("7BO9hOnqtIRWHnJX-lQ3ULWSHTIZcbhlwVwNc44KEZE").build());
                return null;
            } else if (wxMessage.getContent().equals("9") || wxMessage.getContent().equals("客服")) {
                wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UJ5Us_HRYXI7gYgscqlgqzE").build());
                return null;
            } else {
                return new TextBuilder().build(content, wxMessage, wxMpService);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
            return null;
        }
        //        try {
        //            content = "收到信息内容：" + objectMapper.writeValueAsString(wxMessage);
        //        } catch (JsonProcessingException e) {
        //            e.printStackTrace();
        //        }
        //
        //        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
