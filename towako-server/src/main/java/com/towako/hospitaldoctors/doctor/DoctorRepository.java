package com.towako.hospitaldoctors.doctor;

import com.cartisan.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends BaseRepository<Doctor, Long> {
    boolean existsByPhone(String phone);

    Optional<Doctor> findByUserId(Long userId);

    Page<Doctor> findByIdIn(List<Long> hospitalIds, Pageable pageable);
}
