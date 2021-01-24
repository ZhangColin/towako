package com.towako.channel.doctor;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.channel.doctor.request.DoctorParam;
import com.towako.channel.doctor.request.DoctorQuery;
import com.towako.channel.doctor.response.DoctorConverter;
import com.towako.channel.doctor.response.DoctorDto;
import com.towako.channel.wechatqrcode.WeChatQrCodeAppService;
import com.towako.channel.wechatqrcode.response.WeChatQrCodeDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;
import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
public class DoctorAppService {
    public static final String ERR_NAME_EXISTS = "医生已存在。";
    public static final String CHANNEL_TYPE = "DOCTOR";

    private final DoctorRepository repository;
    private final WeChatQrCodeAppService weChatQrCodeAppService;
    private final SnowflakeIdWorker idWorker;

    private final DoctorConverter doctorConverter = DoctorConverter.CONVERTER;

    public DoctorAppService(DoctorRepository repository, WeChatQrCodeAppService weChatQrCodeAppService, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.weChatQrCodeAppService = weChatQrCodeAppService;
        this.idWorker = idWorker;
    }

    public PageResult<DoctorDto> searchDoctors(@NonNull DoctorQuery doctorQuery, @NonNull Pageable pageable) {
        final Page<Doctor> searchResult = repository.findAll(querySpecification(doctorQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        final List<DoctorDto> doctors = doctorConverter.convert(searchResult.getContent());

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(
                doctors.stream().map(DoctorDto::getId).collect(toList()), CHANNEL_TYPE);

        doctors.forEach(doctorDto -> qrCodeDtos.stream()
                .filter(qrCodeDto->qrCodeDto.getChannelId().equals(doctorDto.getId()))
                .findFirst().ifPresent(qrCodeDto->{
            doctorDto.setTicket(qrCodeDto.getTicket());
            doctorDto.setImageUrl(qrCodeDto.getImageUrl());
            doctorDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
            doctorDto.setUrl(qrCodeDto.getUrl());
        }));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                doctors);
    }

    @Transactional(rollbackOn = Exception.class)
    public void addDoctor(DoctorParam doctorParam) {
        if (repository.existsByName(doctorParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final long doctorId = idWorker.nextId();

        weChatQrCodeAppService.applyWechatQrCode(doctorId, CHANNEL_TYPE);

        final Doctor doctor = new Doctor(doctorId, doctorParam.getName());
        repository.save(doctor);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editDoctor(Long id, DoctorParam doctorParam) {
        if (repository.existsByNameAndIdNot(doctorParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Doctor doctor = requirePresent(repository.findById(id));

        doctor.describe(doctorParam.getName());

        repository.save(doctor);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeDoctor(long id) {
        repository.deleteById(id);
    }


    @Transactional(rollbackOn = Exception.class)
    public void enable(Long id) {
        final Doctor doctor = requirePresent(repository.findById(id));
        doctor.enable();
        repository.save(doctor);
    }

    @Transactional(rollbackOn = Exception.class)
    public void disable(Long id) {
        final Doctor doctor = requirePresent(repository.findById(id));
        doctor.disable();
        repository.save(doctor);
    }
}
