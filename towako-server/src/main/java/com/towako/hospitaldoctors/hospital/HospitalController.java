package com.towako.hospitaldoctors.hospital;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

@Api(tags = "医院医生：医院")
@RestController
@RequestMapping("/hospital-doctors/hospitals")
@Validated
@Slf4j
public class HospitalController {
    private final HospitalAppService service;

    public HospitalController(HospitalAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "获取所有医院")
    @GetMapping()
    public ResponseEntity<List<HospitalDto>> getAll() {
        return success(service.getAll());
    }

    @ApiOperation(value = "获取医院")
    @GetMapping("/{id}")
    public ResponseEntity<HospitalDto> getHospital(@ApiParam(value = "医院Id", required = true) @PathVariable Long id){
        return success(service.getHospital(id));
    }

    @ApiOperation(value = "添加医院")
    @PostMapping
    public ResponseEntity<HospitalDto> addHospital(
            @ApiParam(value = "医院信息", required = true) @Validated @RequestBody HospitalParam hospitalParam) {
        return success(service.addHospital(hospitalParam));
    }

    @ApiOperation(value = "编辑医院")
    @PutMapping("/{id}")
    public ResponseEntity<HospitalDto> editHospital(
            @ApiParam(value = "医院Id", required = true) @PathVariable Long id,
            @ApiParam(value = "医院信息", required = true) @Validated @RequestBody HospitalParam hospitalParam) {
        return success(service.editHospital(id, hospitalParam));
    }

    @ApiOperation(value = "删除医院")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeHospital(
            @ApiParam(value = "医院Id", required = true) @PathVariable Long id) {
        service.removeHospital(id);
        return success();
    }
}
