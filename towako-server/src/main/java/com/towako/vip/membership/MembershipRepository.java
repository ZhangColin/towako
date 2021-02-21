package com.towako.vip.membership;

import com.cartisan.repositories.BaseRepository;
import com.towako.vip.membership.domain.Membership;

import java.util.List;

/**
 * @author colin
 */
public interface MembershipRepository extends BaseRepository<Membership, Long> {
    List<Membership> findByIdIn(List<Long> ids);
}
