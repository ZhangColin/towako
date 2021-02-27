package com.towako.traffic.channel;

import com.cartisan.dtos.PageResult;
import com.cartisan.utils.QrCodeUtil;
import com.towako.security.CurrentUser;
import com.towako.traffic.channel.request.ChannelParam;
import com.towako.traffic.channel.request.ChannelQuery;
import com.towako.traffic.channel.response.ChannelBaseInfoDto;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "渠道管理：我的渠道信息")
@RestController
@RequestMapping("/traffic/mychannel")
@Validated
@Slf4j
public class MyChannelController {
    private final ChannelAppService service;
    private final CurrentUser currentUser;

    public MyChannelController(ChannelAppService service, CurrentUser currentUser) {
        this.service = service;
        this.currentUser = currentUser;
    }

    @ApiOperation(value = "我的渠道信息")
    @GetMapping("")
    public ResponseEntity<ChannelDto> myChannel() {
        return success(service.getChannelByUserId(currentUser.getUserId()));
    }



    @ApiOperation(value = "我的渠道二维码")
    @GetMapping("/qrcode")
    public void myChannelQrCode(@RequestParam String channelId, HttpServletResponse response) throws Exception {
        QrCodeUtil.encode("http://channel-h5.lanmedical.com/#/register?pId="+channelId, response.getOutputStream());
    }



}
