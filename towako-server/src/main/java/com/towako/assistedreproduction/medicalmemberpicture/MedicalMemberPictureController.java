package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
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

@Api(tags = "生殖辅助：用户自传病历图片")
@RestController
@RequestMapping("/medicalMemberPictures")
@Validated
@Slf4j
public class MedicalMemberPictureController {
    private final MedicalMemberPictureAppService service;

    public MedicalMemberPictureController(MedicalMemberPictureAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索用户自传病历图片")
    @GetMapping("/search")
    public ResponseEntity<PageResult<MedicalMemberPictureDto>> searchMedicalMemberPictures(
            @ApiParam(value = "查询参数") MedicalMemberPictureQuery medicalMemberPictureQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchMedicalMemberPictures(medicalMemberPictureQuery, pageable));
    }

    @ApiOperation(value = "获取用户自传病历图片")
    @GetMapping("/{id}")
    public ResponseEntity<MedicalMemberPictureDto> getMedicalMemberPicture(@ApiParam(value = "用户自传病历图片Id", required = true) @PathVariable Long id){
        return success(service.getMedicalMemberPicture(id));
    }

    @ApiOperation(value = "添加用户自传病历图片")
    @PostMapping
    public ResponseEntity<MedicalMemberPictureDto> addMedicalMemberPicture(
            @ApiParam(value = "用户自传病历图片信息", required = true) @Validated @RequestBody MedicalMemberPictureParam medicalMemberPictureParam) {
        return success(service.addMedicalMemberPicture(medicalMemberPictureParam));
    }

    @ApiOperation(value = "编辑用户自传病历图片")
    @PutMapping("/{id}")
    public ResponseEntity<MedicalMemberPictureDto> editMedicalMemberPicture(
            @ApiParam(value = "用户自传病历图片Id", required = true) @PathVariable Long id,
            @ApiParam(value = "用户自传病历图片信息", required = true) @Validated @RequestBody MedicalMemberPictureParam medicalMemberPictureParam) {
        return success(service.editMedicalMemberPicture(id, medicalMemberPictureParam));
    }

    @ApiOperation(value = "删除用户自传病历图片")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMedicalMemberPicture(
            @ApiParam(value = "用户自传病历图片Id", required = true) @PathVariable Long id) {
        service.removeMedicalMemberPicture(id);
        return success();
    }
}
