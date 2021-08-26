package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicalMemberPictureRepository extends BaseRepository<MedicalMemberPicture, Long> {
    List<MedicalMemberPicture> findByMemberId(Long memberId);
    List<MedicalMemberPicture> findByMedicalRecordId(Long medicalRecordId);
}
