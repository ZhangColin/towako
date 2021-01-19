package com.towako.system.resource.request;

import com.cartisan.repositories.Condition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class ResourceQuery {
    @ApiModelProperty(value = "根据资源名称、权限编码、Url模糊查询")
    @Condition(blurry = "name,code,url")
    private String blurry;

    @ApiModelProperty(value = "资源分类")
    @Condition(propName = "categoryId", type = Condition.Type.EQUAL)
    private Long categoryId;
}
