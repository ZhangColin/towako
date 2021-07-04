package com.towako.assistedreproduction.medicalteam;

import com.cartisan.dtos.PageResult;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class MedicalTeamAppService {
    private final MedicalTeamRepository repository;

    private final MedicalTeamConverter converter = MedicalTeamConverter.CONVERTER;

    public MedicalTeamAppService(MedicalTeamRepository repository) {
        this.repository = repository;
    }

    public PageResult<MedicalTeamDto> searchMedicalTeams(@NonNull MedicalTeamQuery medicalTeamQuery, @NonNull Pageable pageable) {
        final Page<MedicalTeam> searchResult = repository.findAll(querySpecification(medicalTeamQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public MedicalTeamDto getMedicalTeam(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalTeamDto addMedicalTeam(MedicalTeamParam medicalTeamParam) {
        final MedicalTeam medicalTeam = new MedicalTeam(medicalTeamParam.getMedicalRecordId(),
        medicalTeamParam.getDoctorId(),
        medicalTeamParam.getSort());

        return converter.convert(repository.save(medicalTeam));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalTeamDto editMedicalTeam(Long id, MedicalTeamParam medicalTeamParam) {
        final MedicalTeam medicalTeam = requirePresent(repository.findById(id));

        medicalTeam.describe(medicalTeamParam.getMedicalRecordId(),
        medicalTeamParam.getDoctorId(),
        medicalTeamParam.getSort());

        return converter.convert(repository.save(medicalTeam));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMedicalTeam(long id) {
        repository.deleteById(id);
    }
}
