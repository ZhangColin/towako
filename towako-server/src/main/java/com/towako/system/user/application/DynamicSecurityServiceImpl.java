package com.towako.system.user.application;

import com.cartisan.security.DynamicSecurityService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicSecurityServiceImpl implements DynamicSecurityService {
    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        final ConcurrentHashMap<String, ConfigAttribute> map = new ConcurrentHashMap<>();

//        map.put("/system/users/**", new SecurityConfig("123:test"));

        return map;
    }
}
