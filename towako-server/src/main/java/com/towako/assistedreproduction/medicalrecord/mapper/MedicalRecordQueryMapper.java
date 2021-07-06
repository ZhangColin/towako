package com.towako.assistedreproduction.medicalrecord.mapper;

import com.towako.assistedreproduction.medicalrecord.MedicalRecordDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author colin
 */
public interface MedicalRecordQueryMapper {
    List<MedicalRecordDto> searchMedicalRecord(@Param("currentUserId") Long currentUserId, @Param("blurry") String blurry);
}
