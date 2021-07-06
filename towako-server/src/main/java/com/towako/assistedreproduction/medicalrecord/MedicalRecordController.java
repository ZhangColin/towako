package com.towako.assistedreproduction.medicalrecord;

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

@Api(tags = "生殖辅助：病历")
@RestController
@RequestMapping("/assisted-reproduction/medical-records")
@Validated
@Slf4j
public class MedicalRecordController {
    private final MedicalRecordAppService service;

    public MedicalRecordController(MedicalRecordAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索病历")
    @GetMapping("/search")
    public ResponseEntity<PageResult<MedicalRecordDto>> searchMedicalRecords(
            @ApiParam(value = "查询参数") MedicalRecordQuery medicalRecordQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchMedicalRecords(medicalRecordQuery, pageable));
    }

    @ApiOperation(value = "获取病历")
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDetailDto> getMedicalRecord(@ApiParam(value = "病历Id", required = true) @PathVariable Long id){
        return success(service.getMedicalRecord(id));
    }

    @ApiOperation(value = "添加病历")
    @PostMapping
    public ResponseEntity<MedicalRecordDetailDto> addMedicalRecord(
            @ApiParam(value = "病历信息", required = true) @Validated @RequestBody MedicalRecordParam medicalRecordParam) {
        return success(service.addMedicalRecord(medicalRecordParam));
    }

    @ApiOperation(value = "编辑病历")
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDetailDto> editMedicalRecord(
            @ApiParam(value = "病历Id", required = true) @PathVariable Long id,
            @ApiParam(value = "病历信息", required = true) @Validated @RequestBody MedicalRecordParam medicalRecordParam) {
        return success(service.editMedicalRecord(id, medicalRecordParam));
    }

    @ApiOperation(value = "删除病历")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMedicalRecord(
            @ApiParam(value = "病历Id", required = true) @PathVariable Long id) {
        service.removeMedicalRecord(id);
        return success();
    }
}
