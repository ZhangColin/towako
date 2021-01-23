package com.towako.wx.mp.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MpLogHandler implements WxMpMessageHandler {
    private final ObjectMapper objectMapper;

    public MpLogHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        try {
            log.info("\n接收到请求消息，内容：{}", objectMapper.writeValueAsString(wxMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
