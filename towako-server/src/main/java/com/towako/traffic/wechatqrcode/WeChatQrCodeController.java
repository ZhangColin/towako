package com.towako.traffic.wechatqrcode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "渠道管理：渠道")
@RestController
@RequestMapping("/traffic/weChatQrCode")
@Validated
@Slf4j
public class WeChatQrCodeController {
    private final WeChatQrCodeAppService service;

    public WeChatQrCodeController(WeChatQrCodeAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "更新渠道二维码")
    @PutMapping("/reApply/{channelId}")
    public ResponseEntity<?> reApplyWechatQrCode(
            @ApiParam(value = "渠道Id", required = true) @PathVariable Long channelId) {
        service.reApplyWechatQrCode(channelId);
        return success();
    }

}
