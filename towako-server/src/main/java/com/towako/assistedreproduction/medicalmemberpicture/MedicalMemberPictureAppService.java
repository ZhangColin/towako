package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;
import static java.util.stream.Collectors.toList;

@Service
public class MedicalMemberPictureAppService {
    private final MedicalMemberPictureRepository repository;

    private final MedicalMemberPictureConverter converter = MedicalMemberPictureConverter.CONVERTER;

    public MedicalMemberPictureAppService(MedicalMemberPictureRepository repository) {
        this.repository = repository;
    }

    public PageResult<MedicalMemberPictureDto> searchMedicalMemberPictures(@NonNull MedicalMemberPictureQuery medicalMemberPictureQuery, @NonNull Pageable pageable) {
        final Page<MedicalMemberPicture> searchResult = repository.findAll(querySpecification(medicalMemberPictureQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public MedicalMemberPictureDto getMedicalMemberPicture(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalMemberPictureDto addMedicalMemberPicture(MedicalMemberPictureParam medicalMemberPictureParam) {
        final MedicalMemberPicture medicalMemberPicture = new MedicalMemberPicture(medicalMemberPictureParam.getMedicalRecordId(),
        medicalMemberPictureParam.getMemberId(),
        medicalMemberPictureParam.getUrl());

        return converter.convert(repository.save(medicalMemberPicture));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalMemberPictureDto editMedicalMemberPicture(Long id, MedicalMemberPictureParam medicalMemberPictureParam) {
        final MedicalMemberPicture medicalMemberPicture = requirePresent(repository.findById(id));

        medicalMemberPicture.describe(medicalMemberPictureParam.getMedicalRecordId(),
        medicalMemberPictureParam.getMemberId(),
        medicalMemberPictureParam.getUrl());

        return converter.convert(repository.save(medicalMemberPicture));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMedicalMemberPicture(long id) {
        repository.deleteById(id);
    }
}
