package com.towako.assistedreproduction.medicalteam;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalTeamConverter extends Converter<MedicalTeam, MedicalTeamDto> {
    MedicalTeamConverter CONVERTER = Mappers.getMapper(MedicalTeamConverter.class);
}
