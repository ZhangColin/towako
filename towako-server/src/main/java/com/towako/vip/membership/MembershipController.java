package com.towako.vip.membership;

import com.cartisan.dtos.PageResult;
import com.towako.security.CurrentUser;
import com.towako.vip.membership.response.MembershipDto;
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
@Api(tags = "VIP管理：会员")
@RestController
@RequestMapping("/vip/memberships")
@Validated
@Slf4j
public class MembershipController {
    private final MembershipAppService service;
    private final UpdateMembershipAppService updateMembershipAppService;
    private final SyncYzAppService syncYzAppService;
    private final CurrentUser currentUser;

    public MembershipController(MembershipAppService service, UpdateMembershipAppService updateMembershipAppService,
                                SyncYzAppService syncYzAppService, CurrentUser currentUser) {
        this.service = service;
        this.updateMembershipAppService = updateMembershipAppService;
        this.syncYzAppService = syncYzAppService;
        this.currentUser = currentUser;
    }

    @ApiOperation(value = "搜索会员")
    @GetMapping("/search")
    public ResponseEntity<PageResult<MembershipDto>> searchMemberships(
            @ApiParam(value = "查询参数") MembershipQuery membershipQuery,
            @PageableDefault Pageable pageable) {
        final PageResult<MembershipDto> data = service.searchMemberships(membershipQuery, pageable);
        if (currentUser.getUserId().equals(1512216348590215168L)){
            data.getRows().forEach(membershipDto -> {
                membershipDto.setRecommend(" ");
                membershipDto.setChannel("");
            });
        }
        return success(data);
    }

    @ApiOperation(value = "更新会员微信信息")
    @PostMapping("/updateWechatInfo")
    public ResponseEntity<?> updateWechatInfo() {
        updateMembershipAppService.updateWechatInfo();
        return success();
    }

    @ApiOperation(value = "同步有赞数据的手机号")
    @PutMapping("/syncYzMobile")
    public ResponseEntity<?> syncYzMobile() {
        syncYzAppService.syncYzMobile();
        return success();
    }

    @ApiOperation(value = "同步有赞客户数据")
    @PutMapping("/syncYzVip")
    public ResponseEntity<?> syncYzVip() {
        syncYzAppService.syncYzVip();
        return success();
    }

    @ApiOperation(value = "处理微信有赞重复用户")
    @PutMapping("/removeDuplicateUser")
    public ResponseEntity<?> removeDuplicateUser() {
        syncYzAppService.removeDuplicateUser();
        return success();
    }

}
