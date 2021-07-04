package com.towako.hospitaldoctors.doctor;

import com.cartisan.dtos.PageResult;
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

@Api(tags = "医院医生：医生")
@RestController
@RequestMapping("/doctors")
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

    @ApiOperation(value = "获取医生")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@ApiParam(value = "医生Id", required = true) @PathVariable Long id){
        return success(service.getDoctor(id));
    }

    @ApiOperation(value = "添加医生")
    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(
            @ApiParam(value = "医生信息", required = true) @Validated @RequestBody DoctorParam doctorParam) {
        return success(service.addDoctor(doctorParam));
    }

    @ApiOperation(value = "编辑医生")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> editDoctor(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id,
            @ApiParam(value = "医生信息", required = true) @Validated @RequestBody DoctorParam doctorParam) {
        return success(service.editDoctor(id, doctorParam));
    }

    @ApiOperation(value = "删除医生")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDoctor(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id) {
        service.removeDoctor(id);
        return success();
    }
}
