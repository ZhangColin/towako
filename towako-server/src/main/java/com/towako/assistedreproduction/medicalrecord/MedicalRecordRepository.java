package com.towako.assistedreproduction.medicalrecord;

import com.cartisan.repositories.BaseRepository;

import java.util.Optional;

public interface MedicalRecordRepository extends BaseRepository<MedicalRecord, Long> {
    boolean existsByMemberId(Long memberId);

    Optional<MedicalRecord> findByMemberId(Long memberId);
}
