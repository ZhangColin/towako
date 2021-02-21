package com.towako.vip.membership;

import com.cartisan.dtos.PageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.towako.vip.membership.response.MembershipDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "VIP管理：会员")
@RestController
@RequestMapping("/vip/memberships")
@Validated
@Slf4j
public class MembershipController {
    private final MembershipAppService service;
    private final UpdateMembershipAppService updateMembershipAppService;
    private final SyncYzAppService syncYzAppService;

    public MembershipController(MembershipAppService service, UpdateMembershipAppService updateMembershipAppService, SyncYzAppService syncYzAppService) {
        this.service = service;
        this.updateMembershipAppService = updateMembershipAppService;
        this.syncYzAppService = syncYzAppService;
    }

    @ApiOperation(value = "搜索会员")
    @GetMapping("/search")
    public ResponseEntity<PageResult<MembershipDto>> searchMemberships(
            @ApiParam(value = "查询参数") MembershipQuery membershipQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchMemberships(membershipQuery, pageable));
    }

    @GetMapping("/findByChannel/{channelId}")
    public ResponseEntity<PageResult<MembershipDto>> findByChannelId(@PathVariable Long channelId,
                                                                     @PageableDefault Pageable pageable) {
        return success(service.findByChannelId(channelId, pageable));
    }

    @ApiOperation(value = "更新会员微信信息")
    @PostMapping("/updateWechatInfo")
    public ResponseEntity<?> updateWechatInfo() {
        updateMembershipAppService.updateWechatInfo();
        return success();
    }

    @ApiOperation(value = "同步有赞数据")
    @PutMapping("/syncYzData")
    @Scheduled(cron = "0 0 3 ? * *")
    public ResponseEntity<?> syncYzData() {
        syncYzAppService.syncYzData();
        return success();
    }

}
