package com.towako.assistedreproduction.medicalrecord;

import com.towako.assistedreproduction.treatmentperiod.TreatmentPeriodFullDto;
import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
public class MedicalRecordFullInfoDto {
    MedicalRecordDetailDto medicalRecordDetail;

    List<String> pictures;

    List<TreatmentPeriodFullDto> treatmentPeriods;
}
