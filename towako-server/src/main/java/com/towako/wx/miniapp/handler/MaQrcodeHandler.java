package com.towako.wx.miniapp.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * @author colin
 */
@Component
@Slf4j
public class MaQrcodeHandler implements WxMaMessageHandler {
    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMaMessage, Map<String, Object> map, WxMaService wxMaService, WxSessionManager wxSessionManager) throws WxErrorException {
        try {
            final File file = wxMaService.getQrcodeService().createQrcode("123", 430);
            WxMediaUploadResult uploadResult = wxMaService.getMediaService().uploadMedia("image", file);
            wxMaService.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMaMessage.getFromUser())
                            .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    }
}
