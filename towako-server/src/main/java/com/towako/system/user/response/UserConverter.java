package com.towako.system.user.response;

import com.cartisan.dtos.Converter;
import com.towako.system.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface UserConverter extends Converter<User, UserDto> {
    UserConverter CONVERTER = Mappers.getMapper(UserConverter.class);
}
