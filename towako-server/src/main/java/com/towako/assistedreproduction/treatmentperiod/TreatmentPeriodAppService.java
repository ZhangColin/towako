package com.towako.assistedreproduction.treatmentperiod;

import com.cartisan.dtos.PageResult;
import com.towako.assistedreproduction.inspectionreport.InspectionReportAppService;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class TreatmentPeriodAppService {
    private final TreatmentPeriodRepository repository;
    private final InspectionReportAppService inspectionReportAppService;

    private final TreatmentPeriodConverter converter = TreatmentPeriodConverter.CONVERTER;

    public TreatmentPeriodAppService(TreatmentPeriodRepository repository, InspectionReportAppService inspectionReportAppService) {
        this.repository = repository;
        this.inspectionReportAppService = inspectionReportAppService;
    }

    public List<TreatmentPeriodDto> searchTreatmentPeriods(@NonNull TreatmentPeriodQuery treatmentPeriodQuery) {
        final List<TreatmentPeriod> searchResult = repository.findAll(querySpecification(treatmentPeriodQuery));

        return converter.convert(searchResult);
    }

    public List<TreatmentPeriodFullDto> findByMedicalRecordId(Long medicalRecordId) {
        final List<TreatmentPeriod> treatmentPeriods =
                repository.findByMedicalRecordId(medicalRecordId, Sort.by(Sort.Direction.DESC, "period"));

        return treatmentPeriods.stream().map(treatmentPeriod -> {
            final TreatmentPeriodFullDto treatmentPeriodFullDto = new TreatmentPeriodFullDto();
            treatmentPeriodFullDto.setTreatmentPeriod(converter.convert(treatmentPeriod));
            treatmentPeriodFullDto.setInspectionReports(inspectionReportAppService.findByTreatmentPeriodId(treatmentPeriod.getId()));

            return treatmentPeriodFullDto;
        }).collect(Collectors.toList());
    }

    public TreatmentPeriodDto getTreatmentPeriod(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public TreatmentPeriodDto addTreatmentPeriod(TreatmentPeriodParam treatmentPeriodParam) {
        final TreatmentPeriod treatmentPeriod = new TreatmentPeriod(treatmentPeriodParam.getMedicalRecordId(),
        treatmentPeriodParam.getPeriod(),
        treatmentPeriodParam.getLastMenstrualPeriod(),
        treatmentPeriodParam.getBmi(),
        treatmentPeriodParam.getAmh(),
        treatmentPeriodParam.getPlan());

        return converter.convert(repository.save(treatmentPeriod));
    }

    @Transactional(rollbackOn = Exception.class)
    public TreatmentPeriodDto editTreatmentPeriod(Long id, TreatmentPeriodParam treatmentPeriodParam) {
        final TreatmentPeriod treatmentPeriod = requirePresent(repository.findById(id));

        treatmentPeriod.describe(treatmentPeriodParam.getMedicalRecordId(),
        treatmentPeriodParam.getPeriod(),
        treatmentPeriodParam.getLastMenstrualPeriod(),
        treatmentPeriodParam.getBmi(),
        treatmentPeriodParam.getAmh(),
        treatmentPeriodParam.getPlan());

        return converter.convert(repository.save(treatmentPeriod));
    }

    @Transactional(rollbackOn = Exception.class)
    public TreatmentPeriodDto reportTreatmentPeriod(Long id, TreatmentPeriodReportParam treatmentPeriodReportParam) {
        final TreatmentPeriod treatmentPeriod = requirePresent(repository.findById(id));

        treatmentPeriod.report(treatmentPeriodReportParam.getReport());

        return converter.convert(repository.save(treatmentPeriod));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeTreatmentPeriod(long id) {
        repository.deleteById(id);
    }
}
