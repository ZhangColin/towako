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
import com.towako.system.user.application.UserAppService;
import com.towako.vip.membership.MembershipRepository;
import com.towako.vip.membership.domain.Membership;
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
    private final MembershipRepository membershipRepository;
    private final UserAppService userAppService;
    private final CurrentUser currentUser;
    private final SnowflakeIdWorker idWorker;

    private final MedicalRecordConverter converter = MedicalRecordConverter.CONVERTER;

    public MedicalRecordAppService(MedicalRecordRepository repository, MedicalRecordQueryMapper medicalRecordQueryMapper,
                                   MedicalTeamAppService medicalTeamAppService, TreatmentPeriodAppService treatmentPeriodAppService,
                                   DoctorAppService doctorAppService, MembershipRepository membershipRepository,
                                   UserAppService userAppService,
                                   CurrentUser currentUser, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.medicalRecordQueryMapper = medicalRecordQueryMapper;
        this.medicalTeamAppService = medicalTeamAppService;
        this.treatmentPeriodAppService = treatmentPeriodAppService;
        this.doctorAppService = doctorAppService;
        this.membershipRepository = membershipRepository;
        this.userAppService = userAppService;
        this.currentUser = currentUser;
        this.idWorker = idWorker;
    }

    public PageResult<MedicalRecordDto> searchMedicalRecords(@NonNull MedicalRecordQuery medicalRecordQuery, @NonNull Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());

        final List<MedicalRecordDto> medicalRecordDtos = userAppService.hasRole(currentUser.getUserId(), 8L) ?
                medicalRecordQueryMapper.searchMedicalRecordForManager(medicalRecordQuery.getBlurry())
                : medicalRecordQueryMapper.searchMedicalRecord(currentUser.getUserId(), medicalRecordQuery.getBlurry());
        final PageInfo<MedicalRecordDto> medicalRecordDtoPageInfo = new PageInfo<>(medicalRecordDtos);

        return new PageResult<>(medicalRecordDtoPageInfo.getTotal(), medicalRecordDtoPageInfo.getPages(), medicalRecordDtoPageInfo.getList());
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
                medicalRecordParam.getBirthday(),
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

        final List<Membership> matchMembers = membershipRepository.findByPhone(medicalRecordParam.getPhone());
        matchMembers.stream().findFirst().ifPresent(member -> medicalRecord.setMemberId(member.getId()));

        return converter.convert(repository.save(medicalRecord));
    }

    @Transactional(rollbackOn = Exception.class)
    public void addMedicalRecord(AddMedicalRecordByMembership addMedicalRecordByMembership) {
        if (repository.existsByMemberId(addMedicalRecordByMembership.getMemberId())) {
            return;
        }

        final MedicalRecord medicalRecord = new MedicalRecord(idWorker.nextId(),
                0L,
                "",
                "",
                addMedicalRecordByMembership.getName(),
                addMedicalRecordByMembership.getPhone(),
                "",
                addMedicalRecordByMembership.getBirthday(),
                0,
                "",
                "",
                "",
                "",
                "",
                1);

        medicalRecord.setMemberId(addMedicalRecordByMembership.getMemberId());
        repository.save(medicalRecord);
    }

    @Transactional(rollbackOn = Exception.class)
    public MedicalRecordDetailDto editMedicalRecord(Long id, MedicalRecordParam medicalRecordParam) {
        final MedicalRecord medicalRecord = requirePresent(repository.findById(id));

        medicalRecord.describe(
                medicalRecord.getHospitalId(),
                medicalRecordParam.getRecordNo(),
                medicalRecordParam.getIvf(),
                medicalRecordParam.getName(),
                medicalRecordParam.getPhone(),
                medicalRecordParam.getIdCard(),
                medicalRecordParam.getBirthday(),
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
