package com.towako.hospitaldoctors.doctor.mapper;

import com.towako.hospitaldoctors.hospital.HospitalDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author colin
 */
public interface DoctorQueryMapper {
    List<Long> getDoctorIdsByHospitalId(@Param("hospitalId") Long hospitalId);

    List<HospitalDto> getDoctorHospitals(@Param("doctorId") Long doctorId);

    List<HospitalDto> getHospitalsByUserId(@Param("userId") Long userId);
}
