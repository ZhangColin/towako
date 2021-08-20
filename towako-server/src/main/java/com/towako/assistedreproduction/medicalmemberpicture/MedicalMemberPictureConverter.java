package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalMemberPictureConverter extends Converter<MedicalMemberPicture, MedicalMemberPictureDto> {
    MedicalMemberPictureConverter CONVERTER = Mappers.getMapper(MedicalMemberPictureConverter.class);
}
