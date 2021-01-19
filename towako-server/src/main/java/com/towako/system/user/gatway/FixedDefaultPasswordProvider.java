package com.towako.system.user.gatway;

import com.towako.system.user.domain.DefaultPasswordProvider;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class FixedDefaultPasswordProvider implements DefaultPasswordProvider {
    @Override
    public String generate() {
        return "123456";
    }
}
