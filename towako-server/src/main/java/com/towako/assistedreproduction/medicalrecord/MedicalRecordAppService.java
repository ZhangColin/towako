package com.towako.assistedreproduction.medicalrecord;

import com.cartisan.dtos.PageResult;
import com.cartisan.utils.SnowflakeIdWorker;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class MedicalRecordAppService {
    private final MedicalRecordRepository repository;
    private final SnowflakeIdWorker idWorker;

    private final MedicalRecordConverter converter = MedicalRecordConverter.CONVERTER;

    public MedicalRecordAppService(MedicalRecordRepository repository, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public PageResult<MedicalRecordDto> searchMedicalRecords(@NonNull MedicalRecordQuery medicalRecordQuery, @NonNull Pageable pageable) {
        final Page<MedicalRecord> searchResult = repository.findAll(querySpecification(medicalRecordQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public MedicalRecordDto getMedicalRecord(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalRecordDto addMedicalRecord(MedicalRecordParam medicalRecordParam) {
        final MedicalRecord medicalRecord = new MedicalRecord(idWorker.nextId(),
        medicalRecordParam.getRecordNo(),
        medicalRecordParam.getIvf(),
        medicalRecordParam.getName(),
        medicalRecordParam.getPhone(),
        medicalRecordParam.getIdCard(),
        medicalRecordParam.getAge(),
        medicalRecordParam.getMainAppeal(),
        medicalRecordParam.getHpi(),
        medicalRecordParam.getMedicalHistory(),
        medicalRecordParam.getMan(),
        medicalRecordParam.getNation(),
        medicalRecordParam.getMaritalStatus());

        return converter.convert(repository.save(medicalRecord));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalRecordDto editMedicalRecord(Long id, MedicalRecordParam medicalRecordParam) {
        final MedicalRecord medicalRecord = requirePresent(repository.findById(id));

        medicalRecord.describe(medicalRecordParam.getRecordNo(),
        medicalRecordParam.getIvf(),
        medicalRecordParam.getName(),
        medicalRecordParam.getPhone(),
        medicalRecordParam.getIdCard(),
        medicalRecordParam.getAge(),
        medicalRecordParam.getMainAppeal(),
        medicalRecordParam.getHpi(),
        medicalRecordParam.getMedicalHistory(),
        medicalRecordParam.getMan(),
        medicalRecordParam.getNation(),
        medicalRecordParam.getMaritalStatus());

        return converter.convert(repository.save(medicalRecord));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMedicalRecord(long id) {
        repository.deleteById(id);
    }
}
