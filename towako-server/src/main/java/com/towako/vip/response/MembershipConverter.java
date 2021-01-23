package com.towako.vip.response;

import com.cartisan.dtos.Converter;
import com.towako.vip.domain.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface MembershipConverter extends Converter<Membership, MembershipDto> {
    MembershipConverter CONVERTER = Mappers.getMapper(MembershipConverter.class);
}
