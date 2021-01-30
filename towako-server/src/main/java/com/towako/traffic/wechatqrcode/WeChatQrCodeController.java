package com.towako.traffic.wechatqrcode;

import com.cartisan.dtos.PageResult;
import com.towako.traffic.channel.ChannelAppService;
import com.towako.traffic.channel.request.ChannelParam;
import com.towako.traffic.channel.request.ChannelQuery;
import com.towako.traffic.channel.response.ChannelDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
