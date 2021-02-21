package com.towako.traffic.recommend.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * @author colin
 */
@Data
public class RecommendDto {
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "推荐时间")
    private LocalDateTime recommendDate;
}
