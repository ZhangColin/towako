package com.towako.assistedreproduction.medicalteam;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class MedicalTeamAppService {
    private final MedicalTeamRepository repository;

    private final MedicalTeamConverter converter = MedicalTeamConverter.CONVERTER;

    public MedicalTeamAppService(MedicalTeamRepository repository) {
        this.repository = repository;
    }

    public List<MedicalTeamDto> searchMedicalTeams(@NonNull MedicalTeamQuery medicalTeamQuery) {
        final List<MedicalTeam> searchResult = repository.findAll(querySpecification(medicalTeamQuery));

        return converter.convert(searchResult);
    }

    public MedicalTeamDto getMedicalTeam(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalTeamDto addMedicalTeam(MedicalTeamParam medicalTeamParam) {
        if (repository.existsByMedicalRecordIdAndDoctorId(medicalTeamParam.getMedicalRecordId(), medicalTeamParam.getDoctorId())) {
            return null;
        }

        final MedicalTeam medicalTeam = new MedicalTeam(medicalTeamParam.getMedicalRecordId(),
                medicalTeamParam.getDoctorId(),
                medicalTeamParam.getSort());

        return converter.convert(repository.save(medicalTeam));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalTeamDto editMedicalTeam(Long id, MedicalTeamParam medicalTeamParam) {
        final MedicalTeam medicalTeam = requirePresent(repository.findById(id));

        medicalTeam.changeSort(medicalTeamParam.getSort());

        return converter.convert(repository.save(medicalTeam));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMedicalTeam(long id) {
        repository.deleteById(id);
    }
}
