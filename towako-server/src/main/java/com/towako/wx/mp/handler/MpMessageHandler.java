package com.towako.wx.mp.handler;

import com.towako.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author colin
 */
@Component
@Slf4j
public class MpMessageHandler implements WxMpMessageHandler {
    public MpMessageHandler() {

    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            String content = "感谢关注优生慧公众平台，在这里我们将为诸多备孕家庭提供一系列的国内外医生大咖助孕宝典、就医指南以及优生通道。平台将不定期发放患者福利，敬请期待！\n\n"
                    + "回复数字了解详情：\n"
                    + "1. 上海方法介绍\n"
                    + "2. 东方医院初诊指南\n"
                    + "3. 九院初诊指南\n"
                    + "4. 匡延平主任之试管问答\n"
                    + "5. 好孕喜报\n"
                    + "6. 胚胎实验室的秘密\n"
                    + "7. 宫腔镜手术介绍及预约流程\n"
                    + "8. 在线预约挂号\n"
                    + "9. 指定专家亲诊\n\n"
                    + "名医助孕，智慧优生！\n";

            try {

                if (wxMessage.getContent().equals("1")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UFw-KP1-Q0eyhqIU9Pq4WtM").build());
                    return null;
                } else if (wxMessage.getContent().equals("2")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UDtbWykLPbtxNJMhmzv2vE0").build());
                    return null;
                } else if (wxMessage.getContent().equals("3")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UNNx-eEnSvQm1ZT-fVTlbZA").build());
                    return null;
                } else if (wxMessage.getContent().equals("4")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UI2N5-9a0eas9Sx8sfGQGAQ").build());
                    return null;
                } else if (wxMessage.getContent().equals("5")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MINIPROGRAMPAGE().toUser(wxMessage.getFromUser()).appId("wx48291232e4e2b753").title("好孕喜报").pagePath("home/pages/custom/custom?kdtId=92295863&deptId=92296185&designId=210602005643").thumbMediaId("7BO9hOnqtIRWHnJX-lQ3UAz9M7YM1SPUoTZg4xcHMJo").build());
                    return null;
                } else if (wxMessage.getContent().equals("6")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MINIPROGRAMPAGE().toUser(wxMessage.getFromUser()).appId("wx48291232e4e2b753").title("胚胎实验室的秘密").pagePath("goods/pages/service/details/normal/dept/index?is_share=1&deptId=92296185&pid=789302236&keyword=3nkn4v03qkkqf").thumbMediaId("7BO9hOnqtIRWHnJX-lQ3UIlN9OjMiywG9IWq5Aw7TIc").build());
                    return null;
                } else if (wxMessage.getContent().equals("7")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(wxMessage.getFromUser()).mediaId("7BO9hOnqtIRWHnJX-lQ3UACIwkVOwhe0id5KJGBSSPw").build());
                    return null;
                } else if (wxMessage.getContent().equals("8")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MINIPROGRAMPAGE().toUser(wxMessage.getFromUser()).appId("wx48291232e4e2b753").title("在线预约挂号").pagePath("pages/reserve/quick/quick?deptId=92296185").thumbMediaId("7BO9hOnqtIRWHnJX-lQ3UHjxL4GzEH8kd1WXQnXaR2w").build());
                    return null;
                } else if (wxMessage.getContent().equals("9")) {
                    wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MINIPROGRAMPAGE().toUser(wxMessage.getFromUser()).appId("wx48291232e4e2b753").title("指定专家亲诊").pagePath("goods/pages/service/details/normal/dept/index?is_share=1&deptId=92296185&pid=789302236&keyword=2fvnvotcoub3b").thumbMediaId("7BO9hOnqtIRWHnJX-lQ3UHGiRWUFceHq_a9JkwQHu7s").build());
                    return null;
                } else {
                    return new TextBuilder().build(content, wxMessage, wxMpService);
                }
            } catch (WxErrorException e) {
                e.printStackTrace();

            }
        }
        return null;

    }

}
