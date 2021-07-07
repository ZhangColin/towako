package com.towako.assistedreproduction.inspectionreport;

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

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

@Api(tags = "生殖辅助：检查报告")
@RestController
@RequestMapping("/inspectionReports")
@Validated
@Slf4j
public class InspectionReportController {
    private final InspectionReportAppService service;

    public InspectionReportController(InspectionReportAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索检查报告")
    @GetMapping("/search")
    public ResponseEntity<List<InspectionReportDto>> searchInspectionReports(
            @ApiParam(value = "查询参数") @Validated InspectionReportQuery inspectionReportQuery) {
        return success(service.searchInspectionReports(inspectionReportQuery));
    }

    @ApiOperation(value = "获取检查报告")
    @GetMapping("/{id}")
    public ResponseEntity<InspectionReportDto> getInspectionReport(@ApiParam(value = "检查报告Id", required = true) @PathVariable Long id){
        return success(service.getInspectionReport(id));
    }

    @ApiOperation(value = "添加检查报告")
    @PostMapping
    public ResponseEntity<InspectionReportDto> addInspectionReport(
            @ApiParam(value = "检查报告信息", required = true) @Validated @RequestBody InspectionReportParam inspectionReportParam) {
        return success(service.addInspectionReport(inspectionReportParam));
    }

    @ApiOperation(value = "编辑检查报告")
    @PutMapping("/{id}")
    public ResponseEntity<InspectionReportDto> editInspectionReport(
            @ApiParam(value = "检查报告Id", required = true) @PathVariable Long id,
            @ApiParam(value = "检查报告信息", required = true) @Validated @RequestBody InspectionReportParam inspectionReportParam) {
        return success(service.editInspectionReport(id, inspectionReportParam));
    }

    @ApiOperation(value = "删除检查报告")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeInspectionReport(
            @ApiParam(value = "检查报告Id", required = true) @PathVariable Long id) {
        service.removeInspectionReport(id);
        return success();
    }
}
