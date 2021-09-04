package com.towako.assistedreproduction.medicalmemberpicture;

import com.towako.security.CurrentUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
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

    public List<PictureGroupDto> getMedicalMemberPictures() {
        final List<MedicalMemberPicture> pictures = repository.findByMemberId(currentUser.getUserId());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return pictures.stream().collect(Collectors.groupingBy(p -> df.format(p.getCreateDateTime()),
                        Collectors.mapping(converter::convert, toList())))
                .entrySet().stream().map(e -> new PictureGroupDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(PictureGroupDto::getGroup).reversed())
                .collect(toList());
    }

    public List<PictureGroupDto> getMedicalMemberPicturesByMedicalRecordId(Long medicalRecordId) {
        final List<MedicalMemberPicture> pictures = repository.findByMedicalRecordId(medicalRecordId);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return pictures.stream().collect(Collectors.groupingBy(p -> df.format(p.getCreateDateTime()),
                        Collectors.mapping(converter::convert, toList())))
                .entrySet().stream().map(e -> new PictureGroupDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(PictureGroupDto::getGroup).reversed())
                .collect(toList());
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
