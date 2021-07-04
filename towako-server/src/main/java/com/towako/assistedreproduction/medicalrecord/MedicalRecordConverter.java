package com.towako.assistedreproduction.medicalrecord;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalRecordConverter extends Converter<MedicalRecord, MedicalRecordDto> {
    MedicalRecordConverter CONVERTER = Mappers.getMapper(MedicalRecordConverter.class);
}
