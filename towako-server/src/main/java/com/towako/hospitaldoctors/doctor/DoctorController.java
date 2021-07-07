package com.towako.hospitaldoctors.doctor;

import com.cartisan.dtos.PageResult;
import com.towako.hospitaldoctors.hospital.HospitalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

@Api(tags = "医院医生：医生")
@RestController
@RequestMapping("/hospital-doctors/doctors")
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

    @ApiOperation(value = "搜索医生列表")
    @GetMapping("/list")
    public ResponseEntity<List<DoctorDto>> searchDoctors(
            @ApiParam(value = "查询参数") DoctorQuery doctorQuery) {
        return success(service.searchDoctors(doctorQuery));
    }

    @ApiOperation(value = "获取医生")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@ApiParam(value = "医生Id", required = true) @PathVariable Long id){
        return success(service.getDoctor(id));
    }

    @ApiOperation(value = "获取医生所属医院")
    @GetMapping("/{id}/hospitals")
    public ResponseEntity<List<HospitalDto>> getDoctorHospitals(@ApiParam(value = "医生Id", required = true) @PathVariable Long id){
        return success(service.getDoctorHospitals(id));
    }

    @ApiOperation(value = "获取当前用户所属医院")
    @GetMapping("/myHospitals")
    public ResponseEntity<List<HospitalDto>> getMyHospitals(){
        return success(service.getMyHospitals());
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

//    @ApiOperation(value = "获取分配医院")
//    @GetMapping("/{id}/hospitals")
//    public ResponseEntity<?> getHospitals(
//            @ApiParam(value = "医生Id", required = true) @PathVariable Long id) {
//        return success(service.getHospitals(id));
//    }

    @ApiOperation(value = "分配医院")
    @PutMapping("/{id}/hospitals")
    public ResponseEntity<?> assignHospitals(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id,
            @ApiParam(value = "医院Ids", required = true) @Validated @RequestBody AssignHospitalsCommand command) {
        service.assignHospitals(id, command.getHospitalIds());
        return success();
    }

    @ApiOperation(value = "删除医生")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDoctor(
            @ApiParam(value = "医生Id", required = true) @PathVariable Long id) {
        service.removeDoctor(id);
        return success();
    }
}
