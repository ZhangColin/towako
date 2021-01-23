package com.towako.vip.response;

import com.towako.vip.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

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
}
