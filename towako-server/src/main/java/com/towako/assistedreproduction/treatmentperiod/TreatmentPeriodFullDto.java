package com.towako.assistedreproduction.treatmentperiod;

import com.towako.assistedreproduction.inspectionreport.InspectionReportDto;
import lombok.Data;

import java.util.List;

@Data
public class TreatmentPeriodFullDto {
    TreatmentPeriodDto treatmentPeriod;

    List<InspectionReportDto> inspectionReports;
}
