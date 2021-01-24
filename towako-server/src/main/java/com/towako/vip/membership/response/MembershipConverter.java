package com.towako.vip.membership.response;

import com.cartisan.dtos.Converter;
import com.towako.vip.membership.domain.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface MembershipConverter extends Converter<Membership, MembershipDto> {
    MembershipConverter CONVERTER = Mappers.getMapper(MembershipConverter.class);
}
