package com.towako.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public WxJsapiSignature signature(@RequestBody SignatureRequest request) throws WxErrorException {
        return this.wxMpService.createJsapiSignature(request.getUrl());
    }
    @GetMapping("/getJsapiTicket")
    public String getJsapiTicket(@PathVariable String url) throws WxErrorException {
        final WxJsapiSignature jsapiSignature = this.wxMpService.createJsapiSignature(url);
        System.out.println(jsapiSignature);
        return this.wxMpService.getJsapiTicket(true);
    }
}
