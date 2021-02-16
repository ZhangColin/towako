package com.towako.traffic.channel;

import com.cartisan.dtos.PageResult;
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

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "渠道管理：渠道")
@RestController
@RequestMapping("/traffic/channels")
@Validated
@Slf4j
public class ChannelController {
    private final ChannelAppService service;

    public ChannelController(ChannelAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索渠道")
    @GetMapping("/search")
    public ResponseEntity<PageResult<ChannelDto>> searchChannels(
            @ApiParam(value = "查询参数") ChannelQuery channelQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchChannels(channelQuery, pageable));
    }

    @ApiOperation(value = "获取所有有效渠道")
    @GetMapping("/allEffectiveChannels")
    public ResponseEntity<List<ChannelBaseInfoDto>> findAllEffectiveChannels() {
        return success(service.findAllEffectiveChannels());
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

    @ApiOperation(value = "给渠道创建账号")
    @PutMapping("/{id}/createAccount")
    public ResponseEntity<?> createAccount(
            @ApiParam(value = "渠道Id", required = true) @PathVariable Long id) {
        service.createAccount(id);
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
