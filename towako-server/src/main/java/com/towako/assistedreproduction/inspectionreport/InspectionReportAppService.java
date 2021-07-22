package com.towako.assistedreproduction.inspectionreport;

import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class InspectionReportAppService {
    private final InspectionReportRepository repository;

    private final InspectionReportConverter converter = InspectionReportConverter.CONVERTER;

    public InspectionReportAppService(InspectionReportRepository repository) {
        this.repository = repository;
    }

    public List<InspectionReportDto> searchInspectionReports(@NonNull InspectionReportQuery inspectionReportQuery) {
        final List<InspectionReport> searchResult = repository.findAll(querySpecification(inspectionReportQuery));

        return converter.convert(searchResult);
    }

    public List<InspectionReportDto> findByTreatmentPeriodId(Long treatmentPeriodId){
        return converter.convert(repository.findByTreatmentPeriodId(treatmentPeriodId, Sort.by(Sort.Direction.ASC, "inspectionDate")));
    }

    public InspectionReportDto getInspectionReport(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public InspectionReportDto addInspectionReport(InspectionReportParam inspectionReportParam) {
        final InspectionReport inspectionReport = new InspectionReport(inspectionReportParam.getTreatmentPeriodId(),
                inspectionReportParam.getInspectionDate(),
                inspectionReportParam.getCycleNumber(),
                inspectionReportParam.getLetrozole(),
                inspectionReportParam.getHmg(),
                inspectionReportParam.getHcg(),
                inspectionReportParam.getDecapeptyl(),
                inspectionReportParam.getMpa(),
                inspectionReportParam.getCc(),
                inspectionReportParam.getGanirelix(),
                inspectionReportParam.getFemoston(),
                inspectionReportParam.getTadalafil(),
                inspectionReportParam.getIntima(),
                inspectionReportParam.getIntimaType(),
                inspectionReportParam.getLof1(),
                inspectionReportParam.getLof2(),
                inspectionReportParam.getLof3(),
                inspectionReportParam.getLof4(),
                inspectionReportParam.getLof5(),
                inspectionReportParam.getLof6(),
                inspectionReportParam.getRof1(),
                inspectionReportParam.getRof2(),
                inspectionReportParam.getRof3(),
                inspectionReportParam.getRof4(),
                inspectionReportParam.getRof5(),
                inspectionReportParam.getRof6(),
                inspectionReportParam.getFsh(),
                inspectionReportParam.getLh(),
                inspectionReportParam.getE2(),
                inspectionReportParam.getT(),
                inspectionReportParam.getP(),
                inspectionReportParam.getPrl(),
                inspectionReportParam.getBhcg(),
                inspectionReportParam.getLeucorrhea(),
                inspectionReportParam.getTakeOvumDate());

        return converter.convert(repository.save(inspectionReport));
    }

    @Transactional(rollbackOn = Exception.class)
    public InspectionReportDto editInspectionReport(Long id, InspectionReportParam inspectionReportParam) {
        final InspectionReport inspectionReport = requirePresent(repository.findById(id));

        inspectionReport.describe(inspectionReportParam.getTreatmentPeriodId(),
                inspectionReportParam.getInspectionDate(),
                inspectionReportParam.getCycleNumber(),
                inspectionReportParam.getLetrozole(),
                inspectionReportParam.getHmg(),
                inspectionReportParam.getHcg(),
                inspectionReportParam.getDecapeptyl(),
                inspectionReportParam.getMpa(),
                inspectionReportParam.getCc(),
                inspectionReportParam.getGanirelix(),
                inspectionReportParam.getFemoston(),
                inspectionReportParam.getTadalafil(),
                inspectionReportParam.getIntima(),
                inspectionReportParam.getIntimaType(),
                inspectionReportParam.getLof1(),
                inspectionReportParam.getLof2(),
                inspectionReportParam.getLof3(),
                inspectionReportParam.getLof4(),
                inspectionReportParam.getLof5(),
                inspectionReportParam.getLof6(),
                inspectionReportParam.getRof1(),
                inspectionReportParam.getRof2(),
                inspectionReportParam.getRof3(),
                inspectionReportParam.getRof4(),
                inspectionReportParam.getRof5(),
                inspectionReportParam.getRof6(),
                inspectionReportParam.getFsh(),
                inspectionReportParam.getLh(),
                inspectionReportParam.getE2(),
                inspectionReportParam.getT(),
                inspectionReportParam.getP(),
                inspectionReportParam.getPrl(),
                inspectionReportParam.getBhcg(),
                inspectionReportParam.getLeucorrhea(),
                inspectionReportParam.getTakeOvumDate());

        return converter.convert(repository.save(inspectionReport));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeInspectionReport(long id) {
        repository.deleteById(id);
    }
}
