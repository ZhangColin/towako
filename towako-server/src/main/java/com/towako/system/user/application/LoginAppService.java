package com.towako.system.user.application;

import com.cartisan.security.LoginService;
import com.cartisan.utils.AesUtil;
import com.towako.security.CurrentUser;
import com.towako.system.user.UserRepository;
import com.towako.system.user.mapper.UserQueryMapper;
import com.towako.system.user.request.LoginCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class LoginAppService{
    private final LoginService loginService;
    private final CurrentUser currentUser;
    private final UserQueryMapper userQueryMapper;
    private final UserRepository userRepository;

    public LoginAppService(LoginService loginService, CurrentUser currentUser, UserQueryMapper userQueryMapper, UserRepository userRepository) {
        this.loginService = loginService;
        this.currentUser = currentUser;
        this.userQueryMapper = userQueryMapper;
        this.userRepository = userRepository;
    }

    public Map<String, Object> login(LoginCommand loginCommand) {
        final String token = loginService.login(loginCommand.getUsername(), AesUtil.aesDecode(loginCommand.getPassword()));

        HashMap<String, Object> data = new HashMap<>();
        data.put("token", token);

        return data;
    }

    public void logout() {
        loginService.logoutByUsername(currentUser.getUsername());
    }

    public Map<String, Object> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("userId", currentUser.getUserId().toString());
        info.put("roles", userQueryMapper.getUserAuthorities(currentUser.getUserId()));
        info.put("menus", userQueryMapper.getUserMenus(currentUser.getUserId()));
        info.put("avatar", requirePresent(userRepository.findById(currentUser.getUserId())).getAvatar());

        return info;
    }

}
