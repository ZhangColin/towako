package com.towako.system.dict.response;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class DictItemDto {
    private String label;
    private String value;
    private Integer sort;
}
