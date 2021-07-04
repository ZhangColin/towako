package com.towako.hospitaldoctors.doctor;

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
public class DoctorAppService {
    private final DoctorRepository repository;
    private final SnowflakeIdWorker idWorker;

    private final DoctorConverter converter = DoctorConverter.CONVERTER;

    public DoctorAppService(DoctorRepository repository, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public PageResult<DoctorDto> searchDoctors(@NonNull DoctorQuery doctorQuery, @NonNull Pageable pageable) {
        final Page<Doctor> searchResult = repository.findAll(querySpecification(doctorQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public DoctorDto getDoctor(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public DoctorDto addDoctor(DoctorParam doctorParam) {
        final Doctor doctor = new Doctor(idWorker.nextId(),
        doctorParam.getUserId(),
        doctorParam.getName(),
        doctorParam.getPhone(),
        doctorParam.getTitle(),
        doctorParam.getStatus());

        return converter.convert(repository.save(doctor));
    }

    @Transactional(rollbackOn = Exception.class)
    public DoctorDto editDoctor(Long id, DoctorParam doctorParam) {
        final Doctor doctor = requirePresent(repository.findById(id));

        doctor.describe(doctorParam.getUserId(),
        doctorParam.getName(),
        doctorParam.getPhone(),
        doctorParam.getTitle(),
        doctorParam.getStatus());

        return converter.convert(repository.save(doctor));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeDoctor(long id) {
        repository.deleteById(id);
    }
}
