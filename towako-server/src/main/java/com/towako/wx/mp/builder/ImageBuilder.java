package com.towako.wx.mp.builder;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author colin
 */
@Slf4j
public class ImageBuilder {

    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage,
                                   WxMpService service) {

        WxMpXmlOutImageMessage m = WxMpXmlOutMessage.IMAGE().mediaId(content)
            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
            .build();

        return m;
    }

}
