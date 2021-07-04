package com.towako.hospitaldoctors.hospital;

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
public class HospitalAppService {
    private final HospitalRepository repository;

    private final HospitalConverter converter = HospitalConverter.CONVERTER;

    public HospitalAppService(HospitalRepository repository) {
        this.repository = repository;
    }

    public PageResult<HospitalDto> searchHospitals(@NonNull HospitalQuery hospitalQuery, @NonNull Pageable pageable) {
        final Page<Hospital> searchResult = repository.findAll(querySpecification(hospitalQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public HospitalDto getHospital(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public HospitalDto addHospital(HospitalParam hospitalParam) {
        final Hospital hospital = new Hospital(hospitalParam.getName(),
        hospitalParam.getDescription());

        return converter.convert(repository.save(hospital));
    }

    @Transactional(rollbackOn = Exception.class)
    public HospitalDto editHospital(Long id, HospitalParam hospitalParam) {
        final Hospital hospital = requirePresent(repository.findById(id));

        hospital.describe(hospitalParam.getName(),
        hospitalParam.getDescription());

        return converter.convert(repository.save(hospital));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeHospital(long id) {
        repository.deleteById(id);
    }
}
