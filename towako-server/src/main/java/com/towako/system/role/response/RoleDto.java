package com.towako.system.role.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class RoleDto {
    @ApiModelProperty(value = "角色Id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
