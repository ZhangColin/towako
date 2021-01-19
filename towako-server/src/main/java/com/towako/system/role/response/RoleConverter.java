package com.towako.system.role.response;

import com.cartisan.dtos.Converter;
import com.towako.system.role.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface RoleConverter extends Converter<Role, RoleDto> {
    RoleConverter CONVERTER = Mappers.getMapper(RoleConverter.class);
}
