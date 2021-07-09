package com.towako.assistedreproduction.treatmentperiod;

import com.cartisan.repositories.BaseRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TreatmentPeriodRepository extends BaseRepository<TreatmentPeriod, Long> {
    List<TreatmentPeriod> findByMedicalRecordId(Long medicalRecordId, Sort sort);
}
