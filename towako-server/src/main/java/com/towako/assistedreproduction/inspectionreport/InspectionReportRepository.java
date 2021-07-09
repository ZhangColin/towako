package com.towako.assistedreproduction.inspectionreport;

import com.cartisan.repositories.BaseRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface InspectionReportRepository extends BaseRepository<InspectionReport, Long> {
    List<InspectionReport> findByTreatmentPeriodId(Long treatmentPeriodId, Sort sort);
}
