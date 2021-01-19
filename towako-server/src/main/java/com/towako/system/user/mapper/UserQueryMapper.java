package com.towako.system.user.mapper;


import com.towako.system.menu.MenuDto;

import java.util.List;

/**
 * @author colin
 */
public interface UserQueryMapper {
    List<String> getUserAuthorities(Long userId);

    List<MenuDto> getUserMenus(Long userId);
}
