package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.domains.AbstractEntity;
import com.towako.security.CurrentUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class MedicalMemberPictureAppService {
    private final MedicalMemberPictureRepository repository;
    private final CurrentUser currentUser;

    private final MedicalMemberPictureConverter converter = MedicalMemberPictureConverter.CONVERTER;

    public MedicalMemberPictureAppService(MedicalMemberPictureRepository repository, CurrentUser currentUser) {
        this.repository = repository;
        this.currentUser = currentUser;
    }

//    public Map<String, List<MedicalMemberPictureDto>> getMedicalMemberPictures() {
//        final List<MedicalMemberPicture> pictures = repository.findByMemberId(currentUser.getUserId());
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        final Map<String, List<MedicalMemberPictureDto>> pictureGroups =
//                pictures.stream().sorted(Comparator.comparing(AbstractEntity::getCreateDateTime).reversed())
//                .collect(Collectors.groupingBy(p -> df.format(p.getCreateDateTime()),
//                        Collectors.mapping(converter::convert, toList())));
//
//        return pictureGroups;
//    }

    public List<MedicalMemberPictureDto> getMedicalMemberPictures() {
        final List<MedicalMemberPicture> pictures = repository.findByMemberId(currentUser.getUserId());

        return converter.convert(pictures);
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
