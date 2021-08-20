package com.towako.vip.membership;

import com.cartisan.repositories.BaseRepository;
import com.towako.vip.membership.domain.Membership;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.List;
import java.util.Optional;

/**
 * @author colin
 */
public interface MembershipRepository extends BaseRepository<Membership, Long> {
    List<Membership> findByIdIn(List<Long> ids);

    List<Membership> findByPhone(String phone);
}
