package com.towako.hospitaldoctors.doctor;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DoctorConverter extends Converter<Doctor, DoctorDto> {
    DoctorConverter CONVERTER = Mappers.getMapper(DoctorConverter.class);
}
