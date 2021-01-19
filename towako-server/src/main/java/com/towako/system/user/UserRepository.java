package com.towako.system.user;

import com.cartisan.repositories.BaseRepository;
import com.towako.system.user.domain.User;

import java.util.Optional;

/**
 * @author colin
 */
public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByRealName(String realName);
}
