package com.towako.channel.familyhotel;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.channel.familyhotel.request.FamilyHotelParam;
import com.towako.channel.familyhotel.request.FamilyHotelQuery;
import com.towako.channel.familyhotel.response.FamilyHotelConverter;
import com.towako.channel.familyhotel.response.FamilyHotelDto;
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
public class FamilyHotelAppService {
    public static final String ERR_NAME_EXISTS = "家庭旅馆已存在。";
    public static final String CHANNEL_TYPE = "FAMILYHOTEL";

    private final FamilyHotelRepository repository;
    private final WeChatQrCodeAppService weChatQrCodeAppService;
    private final SnowflakeIdWorker idWorker;

    private final FamilyHotelConverter familyHotelConverter = FamilyHotelConverter.CONVERTER;

    public FamilyHotelAppService(FamilyHotelRepository repository, WeChatQrCodeAppService weChatQrCodeAppService, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.weChatQrCodeAppService = weChatQrCodeAppService;
        this.idWorker = idWorker;
    }

    public PageResult<FamilyHotelDto> searchFamilyHotels(@NonNull FamilyHotelQuery familyHotelQuery, @NonNull Pageable pageable) {
        final Page<FamilyHotel> searchResult = repository.findAll(querySpecification(familyHotelQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        final List<FamilyHotelDto> doctors = familyHotelConverter.convert(searchResult.getContent());

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(
                doctors.stream().map(FamilyHotelDto::getId).collect(toList()), CHANNEL_TYPE);

        doctors.forEach(familyHotelDto -> qrCodeDtos.stream()
                .filter(qrCodeDto->qrCodeDto.getChannelId().equals(familyHotelDto.getId()))
                .findFirst().ifPresent(qrCodeDto->{
            familyHotelDto.setTicket(qrCodeDto.getTicket());
            familyHotelDto.setImageUrl(qrCodeDto.getImageUrl());
            familyHotelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
            familyHotelDto.setUrl(qrCodeDto.getUrl());
        }));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                doctors);
    }

    @Transactional(rollbackOn = Exception.class)
    public void addFamilyHotel(FamilyHotelParam familyHotelParam) {
        if (repository.existsByName(familyHotelParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final long doctorId = idWorker.nextId();

        weChatQrCodeAppService.applyWechatQrCode(doctorId, CHANNEL_TYPE);

        final FamilyHotel familyHotel = new FamilyHotel(doctorId, familyHotelParam.getName());
        repository.save(familyHotel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editFamilyHotel(Long id, FamilyHotelParam familyHotelParam) {
        if (repository.existsByNameAndIdNot(familyHotelParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final FamilyHotel familyHotel = requirePresent(repository.findById(id));

        familyHotel.describe(familyHotelParam.getName());

        repository.save(familyHotel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeFamilyHotel(long id) {
        repository.deleteById(id);
    }


    @Transactional(rollbackOn = Exception.class)
    public void enable(Long id) {
        final FamilyHotel familyHotel = requirePresent(repository.findById(id));
        familyHotel.enable();
        repository.save(familyHotel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void disable(Long id) {
        final FamilyHotel familyHotel = requirePresent(repository.findById(id));
        familyHotel.disable();
        repository.save(familyHotel);
    }
}
