package com.towako.vip.wechateventrecord.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author colin
 */
@Data
public class WeChatEventRecordDto {
    @ApiModelProperty(value = "QrSceneStr")
    private String qrSceneStr;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "触发时间")
    private LocalDateTime eventDate;
}
