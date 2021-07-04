package com.towako.traffic.recommend.response;

import com.cartisan.dtos.Converter;
import com.towako.traffic.recommend.Recommend;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface RecommendConverter extends Converter<Recommend, RecommendDto> {
    RecommendConverter CONVERTER = Mappers.getMapper(RecommendConverter.class);
}
