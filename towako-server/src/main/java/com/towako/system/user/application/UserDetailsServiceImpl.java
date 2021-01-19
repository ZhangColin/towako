package com.towako.system.user.application;

import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.towako.security.CurrentUserInfo;
import com.towako.system.user.UserRepository;
import com.towako.system.user.domain.User;
import com.towako.system.user.mapper.UserQueryMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author colin
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final UserQueryMapper userQueryMapper;

    public UserDetailsServiceImpl(UserRepository repository, UserQueryMapper userQueryMapper) {
        this.repository = repository;
        this.userQueryMapper = userQueryMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = requireUserPresent(repository.findByUsername(username));

        return buildUserDetails(user, userQueryMapper.getUserAuthorities(user.getId()));
    }

    private CurrentUserInfo buildUserDetails(User user, List<String> authorities) {
        return new CurrentUserInfo(user, authorities);
    }

    private User requireUserPresent(Optional<User> userOptional) {
        return userOptional
                .orElseThrow(() -> new CartisanException(CodeMessage.FAIL.fillArgs("用户名或密码不正确")));
    }


}
