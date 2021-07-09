package com.towako.assistedreproduction.medicalrecord;

import com.cartisan.dtos.PageResult;
import com.cartisan.utils.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.towako.assistedreproduction.medicalrecord.mapper.MedicalRecordQueryMapper;
import com.towako.assistedreproduction.medicalteam.MedicalTeamAppService;
import com.towako.assistedreproduction.medicalteam.MedicalTeamParam;
import com.towako.assistedreproduction.treatmentperiod.TreatmentPeriodAppService;
import com.towako.hospitaldoctors.doctor.DoctorAppService;
import com.towako.hospitaldoctors.doctor.DoctorDto;
import com.towako.security.CurrentUser;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;

@Service
public class MedicalRecordAppService {
    private final MedicalRecordRepository repository;
    private final MedicalRecordQueryMapper medicalRecordQueryMapper;
    private final MedicalTeamAppService medicalTeamAppService;
    private final TreatmentPeriodAppService treatmentPeriodAppService;
    private final DoctorAppService doctorAppService;
    private final CurrentUser currentUser;
    private final SnowflakeIdWorker idWorker;

    private final MedicalRecordConverter converter = MedicalRecordConverter.CONVERTER;

    public MedicalRecordAppService(MedicalRecordRepository repository, MedicalRecordQueryMapper medicalRecordQueryMapper,
                                   MedicalTeamAppService medicalTeamAppService, TreatmentPeriodAppService treatmentPeriodAppService,
                                   DoctorAppService doctorAppService,
                                   CurrentUser currentUser, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.medicalRecordQueryMapper = medicalRecordQueryMapper;
        this.medicalTeamAppService = medicalTeamAppService;
        this.treatmentPeriodAppService = treatmentPeriodAppService;
        this.doctorAppService = doctorAppService;
        this.currentUser = currentUser;
        this.idWorker = idWorker;
    }

    public PageResult<MedicalRecordDto> searchMedicalRecords(@NonNull MedicalRecordQuery medicalRecordQuery, @NonNull Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());

        final List<MedicalRecordDto> medicalRecordDtos = medicalRecordQueryMapper.searchMedicalRecord(currentUser.getUserId(), medicalRecordQuery.getBlurry());
        final PageInfo<MedicalRecordDto> medicalRecordDtoPageInfo = new PageInfo<>(medicalRecordDtos);

        return new PageResult<>(medicalRecordDtoPageInfo.getTotal(), medicalRecordDtoPageInfo.getPages(),
                medicalRecordDtoPageInfo.getList());
    }

    public MedicalRecordDetailDto getMedicalRecord(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    public MedicalRecordFullInfoDto getMedicalRecordFullInfo(Long id) {
        final MedicalRecordFullInfoDto medicalRecordFullInfoDto = new MedicalRecordFullInfoDto();
        medicalRecordFullInfoDto.setMedicalRecordDetail(this.getMedicalRecord(id));
        medicalRecordFullInfoDto.setTreatmentPeriods(treatmentPeriodAppService.findByMedicalRecordId(id));

        return medicalRecordFullInfoDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalRecordDetailDto addMedicalRecord(MedicalRecordParam medicalRecordParam) {
        final MedicalRecord medicalRecord = new MedicalRecord(idWorker.nextId(),
                medicalRecordParam.getHospitalId(),
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

        final DoctorDto doctor = doctorAppService.getDoctorByCurrentUser();

        final MedicalTeamParam medicalTeamParam = new MedicalTeamParam();
        medicalTeamParam.setMedicalRecordId(medicalRecord.getId());
        medicalTeamParam.setDoctorId(doctor.getId());
        medicalTeamParam.setSort(10);
        medicalTeamAppService.addMedicalTeam(medicalTeamParam);

        return converter.convert(repository.save(medicalRecord));
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalRecordDetailDto editMedicalRecord(Long id, MedicalRecordParam medicalRecordParam) {
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
