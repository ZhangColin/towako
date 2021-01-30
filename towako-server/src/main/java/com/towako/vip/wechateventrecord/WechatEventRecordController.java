package com.towako.vip.wechateventrecord;

import com.cartisan.dtos.PageResult;
import com.towako.vip.wechateventrecord.response.WeChatEventRecordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "VIP：微信事件记录")
@RestController
@RequestMapping("/vip/wechatEventRecords")
@Validated
@Slf4j
public class WechatEventRecordController {
    private final WechatEventRecordAppService service;

    public WechatEventRecordController(WechatEventRecordAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "用户微信事件列表")
    @GetMapping("/findByMemberId/{memberId}")
    public ResponseEntity<PageResult<WeChatEventRecordDto>> findByMemberId(
            @ApiParam(value = "查询参数") @PathVariable Long memberId,
            @PageableDefault Pageable pageable) {
        return success(service.findByMemberId(memberId, pageable));
    }
}
