package com.towako.assistedreproduction.medicalmemberpicture;

import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.assistedreproduction.medicalrecord.MedicalRecordAppService;
import com.towako.security.CurrentUser;
import com.towako.system.common.TencentCOS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.cartisan.responses.ResponseUtil.success;

@Api(tags = "生殖辅助：用户自传病历图片")
@RestController
@RequestMapping("/assisted-reproduction/medical-member-pictures")
@Validated
@Slf4j
public class MedicalMemberPictureController {
    private final MedicalMemberPictureAppService service;
    private final CurrentUser currentUser;
    private final MedicalRecordAppService medicalRecordAppService;
    private final SnowflakeIdWorker idWorker;
    private final WxMpService wxMpService;

    public MedicalMemberPictureController(MedicalMemberPictureAppService service,
                                          CurrentUser currentUser,
                                          MedicalRecordAppService medicalRecordAppService,
                                          SnowflakeIdWorker idWorker, WxMpService wxMpService) {
        this.service = service;
        this.currentUser = currentUser;
        this.medicalRecordAppService = medicalRecordAppService;
        this.idWorker = idWorker;
        this.wxMpService = wxMpService;
    }

    @ApiOperation(value = "获取当前用户的病历图片")
    @GetMapping()
    public ResponseEntity<List<MedicalMemberPictureDto>> getMedicalMemberPictures() {
        return success(service.getMedicalMemberPictures());
    }

    @ApiOperation(value = "添加用户自传病历图片")
    @PostMapping
    public ResponseEntity<MedicalMemberPictureDto> addMedicalMemberPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("上传的文件为空"));
        }

        final Long medicalRecordId = medicalRecordAppService.getMedicalRecordIdByMemberId(currentUser.getUserId());

        final long pictureId = idWorker.nextId();
        final String key = "/" + medicalRecordId + "/" + pictureId +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        final String url = TencentCOS.uploadfile(file, key);

        MedicalMemberPictureParam medicalMemberPictureParam = new MedicalMemberPictureParam();
        medicalMemberPictureParam.setPictureId(pictureId);
        medicalMemberPictureParam.setMemberId(currentUser.getUserId());
        medicalMemberPictureParam.setMedicalRecordId(medicalRecordId);
        medicalMemberPictureParam.setUrl(url);

        return success(service.addMedicalMemberPicture(medicalMemberPictureParam));
    }


    @ApiOperation(value = "添加用户自传病历图片")
    @PostMapping("/wechatMedia/{mediaId}")
    public ResponseEntity<MedicalMemberPictureDto> addMedicalMemberPictureByWechatMedia(@PathVariable String mediaId) throws IOException, WxErrorException {
        final File file = wxMpService.getMaterialService().mediaDownload(mediaId);
        if (file == null) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("上传的文件为空"));
        }

        final Long medicalRecordId = medicalRecordAppService.getMedicalRecordIdByMemberId(currentUser.getUserId());

        final long pictureId = idWorker.nextId();
        final String key = "/" + medicalRecordId + "/" + pictureId +
                file.getName().substring(file.getName().lastIndexOf("."));

        final String url = TencentCOS.uploadfile(file, key);

        MedicalMemberPictureParam medicalMemberPictureParam = new MedicalMemberPictureParam();
        medicalMemberPictureParam.setPictureId(pictureId);
        medicalMemberPictureParam.setMemberId(currentUser.getUserId());
        medicalMemberPictureParam.setMedicalRecordId(medicalRecordId);
        medicalMemberPictureParam.setUrl(url);

        final MedicalMemberPictureDto data = service.addMedicalMemberPicture(medicalMemberPictureParam);
        return success(data);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("上传的文件为空"));
        }

        final String key = "/" + idWorker.nextId() + "/" + idWorker.nextId() +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return success(TencentCOS.uploadfile(file, key));
    }

    @ApiOperation(value = "删除用户自传病历图片")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMedicalMemberPicture(
            @ApiParam(value = "用户自传病历图片Id", required = true) @PathVariable Long id) {
        service.removeMedicalMemberPicture(id);
        return success();
    }
}
