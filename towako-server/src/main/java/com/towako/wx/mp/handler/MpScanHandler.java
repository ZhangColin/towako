package com.towako.wx.mp.handler;

import com.towako.vip.wechateventrecord.WechatEventRecordAppService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
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
public class MpScanHandler implements WxMpMessageHandler {
    private final WechatEventRecordAppService weChatEventRecordAppService;

    public MpScanHandler(WechatEventRecordAppService weChatEventRecordAppService) {
        this.weChatEventRecordAppService = weChatEventRecordAppService;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        final Map<String, Object> allFieldsMap = wxMpXmlMessage.getAllFieldsMap();
        weChatEventRecordAppService.eventRecord(allFieldsMap.get("FromUserName").toString(),
                allFieldsMap.get("Event").toString(), allFieldsMap.get("EventKey").toString());
        return null;
    }
}
