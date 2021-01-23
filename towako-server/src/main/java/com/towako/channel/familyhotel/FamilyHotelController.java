package com.towako.channel.familyhotel;

import com.cartisan.dtos.PageResult;
import com.towako.channel.familyhotel.request.FamilyHotelParam;
import com.towako.channel.familyhotel.request.FamilyHotelQuery;
import com.towako.channel.familyhotel.response.FamilyHotelDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "渠道管理：家庭旅馆")
@RestController
@RequestMapping("/channel/familyhotels")
@Validated
@Slf4j
public class FamilyHotelController {
    private final FamilyHotelAppService service;

    public FamilyHotelController(FamilyHotelAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索家庭旅馆")
    @GetMapping("/search")
    public ResponseEntity<PageResult<FamilyHotelDto>> searchFamilyHotels(
            @ApiParam(value = "查询参数") FamilyHotelQuery familyHotelQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchFamilyHotels(familyHotelQuery, pageable));
    }

    @ApiOperation(value = "添加家庭旅馆")
    @PostMapping
    public ResponseEntity<?> addFamilyHotel(
            @ApiParam(value = "家庭旅馆信息", required = true) @Validated @RequestBody FamilyHotelParam familyHotelParam) {
        service.addFamilyHotel(familyHotelParam);


        return success();
    }

    @ApiOperation(value = "编辑家庭旅馆")
    @PutMapping("/{id}")
    public ResponseEntity<?> editFamilyHotel(
            @ApiParam(value = "家庭旅馆Id", required = true) @PathVariable Long id,
            @ApiParam(value = "家庭旅馆信息", required = true) @Validated @RequestBody FamilyHotelParam familyHotelParam) {
        service.editFamilyHotel(id, familyHotelParam);
        return success();
    }

    @ApiOperation(value = "删除家庭旅馆")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFamilyHotel(
            @ApiParam(value = "家庭旅馆Id", required = true) @PathVariable long id) {
        service.removeFamilyHotel(id);
        return success();
    }

    @ApiOperation(value = "启用家庭旅馆")
    @PutMapping("/{id}/enable")
    public ResponseEntity<?> enable(
            @ApiParam(value = "家庭旅馆Id", required = true) @PathVariable Long id) {
        service.enable(id);
        return success();
    }

    @ApiOperation(value = "禁用家庭旅馆")
    @PutMapping("/{id}/disable")
    public ResponseEntity<?> disable(
            @ApiParam(value = "家庭旅馆Id", required = true) @PathVariable Long id) {
        service.disable(id);
        return success();
    }
}
