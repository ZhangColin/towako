package com.towako.channel.doctor;

import com.cartisan.dtos.PageResult;
import com.towako.channel.doctor.request.DoctorParam;
import com.towako.channel.doctor.request.DoctorQuery;
import com.towako.channel.doctor.response.DoctorDto;
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
@Api(tags = "渠道管理：医生")
@RestController
@RequestMapping("/channel/doctors")
@Validated
@Slf4j
public class DoctorController {
    private final DoctorAppService service;

    public DoctorController(DoctorAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索医生")
    @GetMapping("/search")
    public ResponseEntity<PageResult<DoctorDto>> searchDoctors(
            @ApiParam(value = "查询参数") DoctorQuery doctorQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchDoctors(doctorQuery, pageable));
    }

    @ApiOperation(value = "添加医生")
    @PostMapping
    public ResponseEntity<?> addDoctor(
            @ApiParam(value = "医生信息", required = true) @Validated @RequestBody DoctorParam doctorParam) {
        service.addDoctor(doctorParam);


        return success();
    }

    @ApiOperation(value = "编辑医生")
    @PutMapping("/{id}")
    public ResponseEntity<?> editDoctor(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id,
            @ApiParam(value = "医生信息", required = true) @Validated @RequestBody DoctorParam doctorParam) {
        service.editDoctor(id, doctorParam);
        return success();
    }

    @ApiOperation(value = "删除医生")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDoctor(
            @ApiParam(value = "医生Id", required = true) @PathVariable long id) {
        service.removeDoctor(id);
        return success();
    }

    @ApiOperation(value = "启用医生")
    @PutMapping("/{id}/enable")
    public ResponseEntity<?> enable(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id) {
        service.enable(id);
        return success();
    }

    @ApiOperation(value = "禁用医生")
    @PutMapping("/{id}/disable")
    public ResponseEntity<?> disable(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id) {
        service.disable(id);
        return success();
    }
}
