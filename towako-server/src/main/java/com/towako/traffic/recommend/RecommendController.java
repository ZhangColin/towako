package com.towako.traffic.recommend;

import com.cartisan.dtos.PageResult;
import com.towako.traffic.channel.ChannelAppService;
import com.towako.traffic.channel.request.ChannelParam;
import com.towako.traffic.channel.request.ChannelQuery;
import com.towako.traffic.channel.response.ChannelDto;
import com.towako.traffic.recommend.response.RecommendDto;
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
@Api(tags = "渠道管理：推荐")
@RestController
@RequestMapping("/traffic/recommends")
@Validated
@Slf4j
public class RecommendController {
    private final RecommendAppService service;

    public RecommendController(RecommendAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "渠道推荐列表")
    @GetMapping("/findByChannelId/{channelId}")
    public ResponseEntity<PageResult<RecommendDto>> findByChannelId(
            @ApiParam(value = "查询参数") @PathVariable Long channelId,
            @PageableDefault Pageable pageable) {
        return success(service.findByChannelId(channelId, pageable));
    }
}
