package com.towako.channel.familyhotel.response;

import com.cartisan.dtos.Converter;
import com.towako.channel.familyhotel.FamilyHotel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface FamilyHotelConverter extends Converter<FamilyHotel, FamilyHotelDto> {
    FamilyHotelConverter CONVERTER = Mappers.getMapper(FamilyHotelConverter.class);
}
