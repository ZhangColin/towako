package com.towako.hospitaldoctors.hospital;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HospitalConverter extends Converter<Hospital, HospitalDto> {
    HospitalConverter CONVERTER = Mappers.getMapper(HospitalConverter.class);
}
