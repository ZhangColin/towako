package com.towako.hospitaldoctors.doctor;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.hospitaldoctors.doctor.mapper.DoctorQueryMapper;
import com.towako.hospitaldoctors.hospital.Hospital;
import com.towako.hospitaldoctors.hospital.HospitalDto;
import com.towako.hospitaldoctors.hospital.HospitalRepository;
import com.towako.security.CurrentUser;
import com.towako.system.role.response.RoleDto;
import com.towako.system.user.application.UserAppService;
import com.towako.system.user.request.AssignRolesCommand;
import com.towako.system.user.request.CreateAccountCommand;
import com.towako.system.user.response.UserDetailDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.utils.AssertionUtil.requirePresent;
import static com.google.common.primitives.Longs.asList;
import static java.util.stream.Collectors.toList;

@Service
public class DoctorAppService {
    public static final String ERR_PHONE_EXISTS = "医生手机号已存在。";

    private final DoctorRepository repository;
    private final HospitalRepository hospitalRepository;
    private final UserAppService userAppService;
    private final DoctorQueryMapper doctorQueryMapper;
    private final CurrentUser currentUser;
    private final SnowflakeIdWorker idWorker;

    private final DoctorConverter converter = DoctorConverter.CONVERTER;

    public DoctorAppService(DoctorRepository repository, HospitalRepository hospitalRepository,
                            UserAppService userAppService, DoctorQueryMapper doctorQueryMapper,
                            CurrentUser currentUser, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.hospitalRepository = hospitalRepository;
        this.userAppService = userAppService;
        this.doctorQueryMapper = doctorQueryMapper;
        this.currentUser = currentUser;
        this.idWorker = idWorker;
    }

    public PageResult<DoctorDto> searchDoctors(@NonNull DoctorQuery doctorQuery, @NonNull Pageable pageable) {
        final Page<Doctor> searchResult = doctorQuery.getHospitalId() != null ?
                repository.findByIdIn(doctorQueryMapper.getDoctorIdsByHospitalId(doctorQuery.getHospitalId()),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())) :
                repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public List<DoctorDto> searchDoctors(@NonNull DoctorQuery doctorQuery) {
        final List<Doctor> searchResult = doctorQuery.getHospitalId() != null ?
                repository.findByIdIn(doctorQueryMapper.getDoctorIdsByHospitalId(doctorQuery.getHospitalId())) :
                repository.findAll();

        return converter.convert(searchResult);
    }

    public DoctorDto getDoctor(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    public DoctorDto getDoctorByCurrentUser() {
        return converter.convert(requirePresent(repository.findByUserId(currentUser.getUserId())));
    }

    public List<Long> getHospitals(Long id) {
        final Doctor doctor = requirePresent(repository.findById(id));

        return doctor.getHospitals().stream().map(DoctorHospital::getHospitalId).collect(toList());
    }

    @Transactional(rollbackOn = Exception.class)
    public DoctorDto addDoctor(DoctorParam doctorParam) {
        if (repository.existsByPhone(doctorParam.getPhone())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_PHONE_EXISTS));
        }

        final Optional<UserDetailDto> userDetailDtoOptional = userAppService.getUserByPhone(doctorParam.getPhone());

        Long userId = 0L;
        if (userDetailDtoOptional.isPresent()) {
            final AssignRolesCommand command = new AssignRolesCommand();
            final UserDetailDto userDetailDto = userDetailDtoOptional.get();

            final List<Long> roleIds = userDetailDto.getRoles().stream().map(RoleDto::getId).collect(toList());
            roleIds.add(7L);
            command.setRoleIds(roleIds);

            userAppService.assignRoles(userDetailDto.getId(), command);

            userId = userDetailDto.getId();
        } else {
            final CreateAccountCommand createAccountCommand = new CreateAccountCommand();
            createAccountCommand.setUsername(doctorParam.getPhone());
            createAccountCommand.setPhone(doctorParam.getPhone());
            createAccountCommand.setRealName(doctorParam.getName());
            createAccountCommand.setRoleIds(asList(7L));
            final UserDetailDto account = userAppService.createAccount(createAccountCommand);

            userId = account.getId();
        }

        final Doctor doctor = new Doctor(idWorker.nextId(),
                userId,
                doctorParam.getName(),
                doctorParam.getPhone(),
                doctorParam.getTitle());

        return converter.convert(repository.save(doctor));
    }

    @Transactional(rollbackOn = Exception.class)
    public DoctorDto editDoctor(Long id, DoctorParam doctorParam) {
        final Doctor doctor = requirePresent(repository.findById(id));

        doctor.describe(
                doctorParam.getName(),
                doctorParam.getTitle());

        return converter.convert(repository.save(doctor));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeDoctor(long id) {
        repository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignHospitals(Long id, List<Long> hospitalIds) {
        final Doctor doctor = requirePresent(repository.findById(id));

        final List<Long> ensureHospitalIds = hospitalRepository.findByIdIn(hospitalIds)
                .stream().map(Hospital::getId).collect(toList());

        doctor.assignHospitals(ensureHospitalIds);
        repository.save(doctor);
    }

    public List<HospitalDto> getDoctorHospitals(Long doctorId) {
        return doctorQueryMapper.getDoctorHospitals(doctorId);
    }

    public List<HospitalDto> getMyHospitals() {
        return doctorQueryMapper.getHospitalsByUserId(currentUser.getUserId());
    }
}
