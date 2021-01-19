package com.towako.system.user.domain;

import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.system.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RegisterServiceTest {

    public static final String USERNAME = "colin";
    public static final String PHONE = "13962830605";
    public static final String EMAIL = "stwyhm@126.com";
    public static final String REALNAME = "colin";

    private UserRepository userRepository;
    private SnowflakeIdWorker idWorker;
    private DefaultPasswordProvider defaultPasswordProvider;
    private RegisterService registerService;
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        idWorker = mock(SnowflakeIdWorker.class);
        defaultPasswordProvider = mock(DefaultPasswordProvider.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registerService = new RegisterService(userRepository, idWorker, defaultPasswordProvider, passwordEncoder);
    }

    @Test
    public void when_register_username_phone_email_all_null_then_throw_exception() {
        assertThatThrownBy(() -> registerService.register(null, null, null, null))
                .isInstanceOf(CartisanException.class)
                .hasMessage("账号、手机、邮箱至少提供一个。");
    }

    @Test
    public void when_register_username_already_exist_then_throw_exception() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // then
        assertThatThrownBy(() -> registerService.register(USERNAME, PHONE, EMAIL, REALNAME))
                .isInstanceOf(CartisanException.class)
                .hasMessage("账号已被占用。");
    }

    @Test
    public void when_register_phone_already_exist_then_throw_exception() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // then
        assertThatThrownBy(() -> registerService.register(USERNAME, PHONE, EMAIL, REALNAME))
                .isInstanceOf(CartisanException.class)
                .hasMessage("手机号已被占用。");
    }

    @Test
    public void when_register_email_already_exist_then_throw_exception() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> registerService.register(USERNAME, PHONE, EMAIL, REALNAME))
                .isInstanceOf(CartisanException.class)
                .hasMessage("邮箱已被占用。");
    }

    @Test
    public void when_register_phone_or_email_is_empty_then_not_call_exists_method() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        // when
        final User user1 = registerService.register(USERNAME, null, null, REALNAME);

        // then
        verify(userRepository, never()).existsByPhone(anyString());
        verify(userRepository, never()).existsByEmail(anyString());

        final User user2 = registerService.register(USERNAME, "", "", REALNAME);

        // then
        verify(userRepository, never()).existsByPhone(anyString());
        verify(userRepository, never()).existsByEmail(anyString());
    }

    @Test
    public void should_be_register_success() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        when(defaultPasswordProvider.generate()).thenReturn("123456");
        when(passwordEncoder.encode(anyString())).thenReturn("123456");

        // when
        final User user1 = registerService.register(USERNAME, PHONE, EMAIL, REALNAME);
        final User user2 = registerService.register(USERNAME, null, null, REALNAME);
        final User user3 = registerService.register(USERNAME, "", "", REALNAME);

        // then
        assertThat(user1).isNotNull();
        assertThat(user1.getId()).isNotNull();

        assertThat(user1.getUsername()).isEqualTo(USERNAME);
        assertThat(user1.getPhone()).isEqualTo(PHONE);
        assertThat(user1.getEmail()).isEqualTo(EMAIL);
        assertThat(user1.getPassword()).isEqualTo("123456");
        assertThat(user1.getRealName()).isEqualTo(REALNAME);

        assertThat(user2).isNotNull();
        assertThat(user2.getPhone()).isEqualTo("");
        assertThat(user2.getEmail()).isEqualTo("");

        assertThat(user3).isNotNull();
        assertThat(user3.getPhone()).isEqualTo("");
        assertThat(user3.getEmail()).isEqualTo("");
    }

    @Test
    public void should_be_ensure_username() {
        // when
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // when
        final User user1 = registerService.register(null, PHONE, EMAIL, REALNAME);
        final User user2 = registerService.register(null, null, EMAIL, REALNAME);

        // then
        assertThat(user1.getUsername()).isEqualTo(PHONE);
        assertThat(user2.getUsername()).isEqualTo(EMAIL);
    }

    @Test
    public void should_be_ensure_real_name() {
        // when
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // when
        final User user = registerService.register(USERNAME, PHONE, EMAIL, null);

        // then
        assertThat(user.getUsername()).isEqualTo(USERNAME);
    }
}
