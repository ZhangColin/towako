package com.towako.channel.doctor.response;

import com.cartisan.dtos.Converter;
import com.towako.channel.doctor.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface DoctorConverter extends Converter<Doctor, DoctorDto> {
    DoctorConverter CONVERTER = Mappers.getMapper(DoctorConverter.class);
}
