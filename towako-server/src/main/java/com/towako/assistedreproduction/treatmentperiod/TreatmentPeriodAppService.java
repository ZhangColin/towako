package com.towako.assistedreproduction.treatmentperiod;

import com.cartisan.dtos.PageResult;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class TreatmentPeriodAppService {
    private final TreatmentPeriodRepository repository;

    private final TreatmentPeriodConverter converter = TreatmentPeriodConverter.CONVERTER;

    public TreatmentPeriodAppService(TreatmentPeriodRepository repository) {
        this.repository = repository;
    }

    public List<TreatmentPeriodDto> searchTreatmentPeriods(@NonNull TreatmentPeriodQuery treatmentPeriodQuery) {
        final List<TreatmentPeriod> searchResult = repository.findAll(querySpecification(treatmentPeriodQuery));

        return converter.convert(searchResult);
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
        treatmentPeriodParam.getPlan(),
        treatmentPeriodParam.getReport(),
        treatmentPeriodParam.getReportDate());

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
        treatmentPeriodParam.getPlan(),
        treatmentPeriodParam.getReport(),
        treatmentPeriodParam.getReportDate());

        return converter.convert(repository.save(treatmentPeriod));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeTreatmentPeriod(long id) {
        repository.deleteById(id);
    }
}
