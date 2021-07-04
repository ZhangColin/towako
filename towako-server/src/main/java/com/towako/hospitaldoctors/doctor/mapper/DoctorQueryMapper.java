package com.towako.hospitaldoctors.doctor.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author colin
 */
public interface DoctorQueryMapper {
    List<Long> getDoctorIdsByHospitalId(@Param("hospitalId") Long hospitalId);
}
