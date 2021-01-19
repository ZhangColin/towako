package com.towako.system.dict.request;

import com.cartisan.repositories.Condition;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class DictQuery {
    @Condition(blurry = "name,code")
    private String blurry;
}
