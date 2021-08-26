package com.towako.assistedreproduction.medicalmemberpicture;

import com.towako.security.CurrentUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MedicalMemberPictureAppService {
    private final MedicalMemberPictureRepository repository;
    private final CurrentUser currentUser;

    private final MedicalMemberPictureConverter converter = MedicalMemberPictureConverter.CONVERTER;

    public MedicalMemberPictureAppService(MedicalMemberPictureRepository repository, CurrentUser currentUser) {
        this.repository = repository;
        this.currentUser = currentUser;
    }

    public List<MedicalMemberPictureDto> getMedicalMemberPictures() {
        return converter.convert(repository.findByMemberId(currentUser.getUserId()));
    }

    public List<MedicalMemberPictureDto> getMedicalMemberPicturesByMedicalRecordId(Long medicalRecordId) {
        return converter.convert(repository.findByMedicalRecordId(medicalRecordId));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalMemberPictureDto addMedicalMemberPicture(MedicalMemberPictureParam medicalMemberPictureParam) {
        final MedicalMemberPicture medicalMemberPicture = new MedicalMemberPicture(
                medicalMemberPictureParam.getPictureId(),
                medicalMemberPictureParam.getMedicalRecordId(),
                medicalMemberPictureParam.getMemberId(),
                medicalMemberPictureParam.getUrl());

        return converter.convert(repository.save(medicalMemberPicture));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMedicalMemberPicture(long id) {
        repository.deleteById(id);
    }
}
