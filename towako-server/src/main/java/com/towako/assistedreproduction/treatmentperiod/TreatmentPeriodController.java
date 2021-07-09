package com.towako.assistedreproduction.treatmentperiod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

@Api(tags = "生殖辅助：疗程")
@RestController
@RequestMapping("/assisted-reproduction/treatment-periods")
@Validated
@Slf4j
public class TreatmentPeriodController {
    private final TreatmentPeriodAppService service;

    public TreatmentPeriodController(TreatmentPeriodAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索疗程")
    @GetMapping("/search")
    public ResponseEntity<List<TreatmentPeriodDto>> searchTreatmentPeriods(
            @ApiParam(value = "查询参数") @Validated TreatmentPeriodQuery treatmentPeriodQuery) {
        return success(service.searchTreatmentPeriods(treatmentPeriodQuery));
    }

    @ApiOperation(value = "获取疗程")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentPeriodDto> getTreatmentPeriod(@ApiParam(value = "疗程Id", required = true) @PathVariable Long id){
        return success(service.getTreatmentPeriod(id));
    }

    @ApiOperation(value = "添加疗程")
    @PostMapping
    public ResponseEntity<TreatmentPeriodDto> addTreatmentPeriod(
            @ApiParam(value = "疗程信息", required = true) @Validated @RequestBody TreatmentPeriodParam treatmentPeriodParam) {
        return success(service.addTreatmentPeriod(treatmentPeriodParam));
    }

    @ApiOperation(value = "编辑疗程")
    @PutMapping("/{id}")
    public ResponseEntity<TreatmentPeriodDto> editTreatmentPeriod(
            @ApiParam(value = "疗程Id", required = true) @PathVariable Long id,
            @ApiParam(value = "疗程信息", required = true) @Validated @RequestBody TreatmentPeriodParam treatmentPeriodParam) {
        return success(service.editTreatmentPeriod(id, treatmentPeriodParam));
    }

    @ApiOperation(value = "诊断报告")
    @PutMapping("/{id}/report")
    public ResponseEntity<TreatmentPeriodDto> reportTreatmentPeriod(
            @ApiParam(value = "疗程Id", required = true) @PathVariable Long id,
            @ApiParam(value = "诊断报告", required = true) @Validated @RequestBody TreatmentPeriodReportParam treatmentPeriodReportParam) {
        return success(service.reportTreatmentPeriod(id, treatmentPeriodReportParam));
    }

    @ApiOperation(value = "删除疗程")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTreatmentPeriod(
            @ApiParam(value = "疗程Id", required = true) @PathVariable Long id) {
        service.removeTreatmentPeriod(id);
        return success();
    }
}
