package com.towako.assistedreproduction.medicalteam;

import com.cartisan.repositories.BaseRepository;

public interface MedicalTeamRepository extends BaseRepository<MedicalTeam, Long> {
    boolean existsByMedicalRecordIdAndDoctorId(Long medicalRecordId, Long doctorId);
}
