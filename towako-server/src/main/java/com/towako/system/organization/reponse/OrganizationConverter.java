package com.towako.system.organization.reponse;

import com.cartisan.dtos.Converter;
import com.towako.system.organization.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface OrganizationConverter extends Converter<Organization, OrganizationDto> {
    OrganizationConverter CONVERTER = Mappers.getMapper(OrganizationConverter.class);

}
