package com.towako.vip.membership.response;

import com.towako.vip.membership.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author colin
 */
@Data
public class MembershipDto {
    @ApiModelProperty(value = "会员Id")
    private Long id;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "性别")
    private Gender gender;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "渠道")
    private String channel;

    @ApiModelProperty(value = "推荐")
    private String recommend;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime createDateTime;
}
