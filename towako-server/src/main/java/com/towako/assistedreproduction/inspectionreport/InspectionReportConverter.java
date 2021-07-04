package com.towako.assistedreproduction.inspectionreport;

import com.cartisan.dtos.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InspectionReportConverter extends Converter<InspectionReport, InspectionReportDto> {
    InspectionReportConverter CONVERTER = Mappers.getMapper(InspectionReportConverter.class);
}
