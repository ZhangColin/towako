package com.towako.system.resource.response;

import com.cartisan.dtos.Converter;
import com.towako.system.resource.domain.ResourceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface ResourceCategoryConverter extends Converter<ResourceCategory, ResourceCategoryDto> {
    ResourceCategoryConverter CONVERTER = Mappers.getMapper(ResourceCategoryConverter.class);
}
