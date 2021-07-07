package com.towako.assistedreproduction.medicalteam;

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

@Api(tags = "生殖辅助：医疗团队")
@RestController
@RequestMapping("/assisted-reproduction/medical-teams")
@Validated
@Slf4j
public class MedicalTeamController {
    private final MedicalTeamAppService service;

    public MedicalTeamController(MedicalTeamAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索医疗团队")
    @GetMapping("/search")
    public ResponseEntity<List<MedicalTeamDto>> searchMedicalTeams(
            @ApiParam(value = "查询参数") @Validated MedicalTeamQuery medicalTeamQuery) {
        return success(service.searchMedicalTeams(medicalTeamQuery));
    }

    @ApiOperation(value = "获取医疗团队")
    @GetMapping("/{id}")
    public ResponseEntity<MedicalTeamDto> getMedicalTeam(@ApiParam(value = "医疗团队Id", required = true) @PathVariable Long id){
        return success(service.getMedicalTeam(id));
    }

    @ApiOperation(value = "添加医疗团队")
    @PostMapping
    public ResponseEntity<MedicalTeamDto> addMedicalTeam(
            @ApiParam(value = "医疗团队信息", required = true) @Validated @RequestBody MedicalTeamParam medicalTeamParam) {
        return success(service.addMedicalTeam(medicalTeamParam));
    }

    @ApiOperation(value = "编辑医疗团队")
    @PutMapping("/{id}")
    public ResponseEntity<MedicalTeamDto> editMedicalTeam(
            @ApiParam(value = "医疗团队Id", required = true) @PathVariable Long id,
            @ApiParam(value = "医疗团队信息", required = true) @Validated @RequestBody MedicalTeamParam medicalTeamParam) {
        return success(service.editMedicalTeam(id, medicalTeamParam));
    }

    @ApiOperation(value = "删除医疗团队")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMedicalTeam(
            @ApiParam(value = "医疗团队Id", required = true) @PathVariable Long id) {
        service.removeMedicalTeam(id);
        return success();
    }
}
