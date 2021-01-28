package com.towako.channel.channel;

import com.cartisan.dtos.PageResult;
import com.towako.channel.channel.request.ChannelParam;
import com.towako.channel.channel.request.ChannelQuery;
import com.towako.channel.channel.response.ChannelDto;
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
@RequestMapping("/channel/channels")
@Validated
@Slf4j
public class ChannelController {
    private final ChannelAppService service;

    public ChannelController(ChannelAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索渠道")
    @GetMapping("/search")
    public ResponseEntity<PageResult<ChannelDto>> searchFamilyHotels(
            @ApiParam(value = "查询参数") ChannelQuery channelQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchChannels(channelQuery, pageable));
    }

    @ApiOperation(value = "添加渠道")
    @PostMapping
    public ResponseEntity<?> addChannel(
            @ApiParam(value = "渠道信息", required = true) @Validated @RequestBody ChannelParam channelParam) {
        service.addChannel(channelParam);


        return success();
    }

    @ApiOperation(value = "编辑渠道")
    @PutMapping("/{id}")
    public ResponseEntity<?> editChannel(
            @ApiParam(value = "渠道Id", required = true) @PathVariable Long id,
            @ApiParam(value = "渠道信息", required = true) @Validated @RequestBody ChannelParam channelParam) {
        service.editChannel(id, channelParam);
        return success();
    }

    @ApiOperation(value = "删除渠道")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeChannel(
            @ApiParam(value = "渠道Id", required = true) @PathVariable long id) {
        service.removeChannel(id);
        return success();
    }

    @ApiOperation(value = "启用渠道")
    @PutMapping("/{id}/enable")
    public ResponseEntity<?> enable(
            @ApiParam(value = "渠道Id", required = true) @PathVariable Long id) {
        service.enable(id);
        return success();
    }

    @ApiOperation(value = "禁用渠道")
    @PutMapping("/{id}/disable")
    public ResponseEntity<?> disable(
            @ApiParam(value = "渠道Id", required = true) @PathVariable Long id) {
        service.disable(id);
        return success();
    }
}