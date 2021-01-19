package com.towako.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author colin
 */
@Component
@Slf4j
public class CurrentUser {
    private CurrentUserInfo getCurrentUser() {
        return (CurrentUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getUserId() {
        return getCurrentUser().getUser().getId();
    }

    public String getUsername() {
        return getCurrentUser().getUsername();
    }

    public String getRealName() {
        return getCurrentUser().getUser().getRealName();
    }
}
