package com.towako.assistedreproduction.treatmentperiod;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreatmentPeriodConverter extends Converter<TreatmentPeriod, TreatmentPeriodDto> {
    TreatmentPeriodConverter CONVERTER = Mappers.getMapper(TreatmentPeriodConverter.class);
}
