package com.towako.system.common;

import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@RestController
@RequestMapping("/cos")
public class CosController {
    private final SnowflakeIdWorker idWorker;

    public CosController(SnowflakeIdWorker idWorker) {
        this.idWorker = idWorker;
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

}
