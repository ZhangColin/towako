package com.towako.vip.membership;

import com.cartisan.dtos.PageResult;
import com.towako.vip.membership.response.MembershipDto;
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

    public MembershipController(MembershipAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索医生")
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

}
