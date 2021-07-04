package com.towako.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@AllArgsConstructor
@Controller
@RequestMapping("/mp/qrcode")
public class WxMpQrCodeController {
    private final WxMpService wxService;

    @RequestMapping("/lastTicket")
    public ResponseEntity<WxMpQrCodeTicket> lastTicket() throws WxErrorException {
        final WxMpQrCodeTicket wxMpQrCodeTicket = this.wxService.getQrcodeService().qrCodeCreateLastTicket("colin_custom_code");
        return success(wxMpQrCodeTicket);
    }
}
