package com.towako.system.user.application;

import com.cartisan.security.LoginService;
import com.towako.system.user.UserRepository;
import com.towako.system.user.domain.AssignService;
import com.towako.system.user.domain.ChangePasswordService;
import com.towako.system.user.domain.RegisterService;
import com.towako.system.user.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.towako.system.user.UserFixture.userOf;
import static org.mockito.Mockito.*;

public class UserAppServiceTest {
    private  RegisterService registerService;
    private  AssignService assignService;
    private  ChangePasswordService changePasswordService;
    private  LoginService loginService;
    private  UserRepository repository;

    private UserAppService userAppService;
    private User user;

    @Before
    public void setUp() {
        registerService = mock(RegisterService.class);
        assignService = mock(AssignService.class);
        changePasswordService = mock(ChangePasswordService.class);
        loginService = mock(LoginService.class);
        repository = mock(UserRepository.class);
        userAppService = new UserAppService(registerService, assignService, changePasswordService, loginService, repository);

        user = userOf();
    }

    @Test
    public void when_reset_password_success_then_user_logout() {
        // given
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        userAppService.resetPassword(1L);

        // then
        verify(changePasswordService).resetPassword(user);
        verify(loginService).logoutByUsername(user.getUsername());
    }
}
