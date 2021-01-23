package com.towako.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jsapi 演示接口的 controller.
 *
 * @author colin
 */
@AllArgsConstructor
@RestController
@RequestMapping("/mp/jsapi")
public class WxMpJsapiController {
    private final WxMpService wxMpService;

    @GetMapping("/getJsapiTicket")
    public String getJsapiTicket(@PathVariable String appid) throws WxErrorException {
        final WxJsapiSignature jsapiSignature = this.wxMpService.switchoverTo(appid).createJsapiSignature("111");
        System.out.println(jsapiSignature);
        return this.wxMpService.getJsapiTicket(true);
    }
}
