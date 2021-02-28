package com.towako.sms;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "短信")
@RestController
@RequestMapping("/captcha")
@Validated
@Slf4j
public class CaptchaController {
    private final ValueOperations<String, String> valueOperations;

    public CaptchaController(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }

    @ApiOperation(value = "发送验证码")
    @GetMapping("/send")
    public ResponseEntity<?> sendCode(@RequestParam String phone){
        try {
            Credential cred = new Credential("AKIDLbgsjNrGlmbXFjKSilbEfs6NSLHxuccP", "vTFduoW45EsrYIuWDjTCiZEmdCfw7221");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "",clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String appid = "1400486413";
            req.setSmsSdkAppid(appid);
            String sign = "优生慧";
            req.setSign(sign);
            String templateID = "871232";
            req.setTemplateID(templateID);

            String[] phoneNumbers = {"+86"+phone};
            req.setPhoneNumberSet(phoneNumbers);

            final String code = vcode();

            String[] templateParams = {code, "30"};
            req.setTemplateParamSet(templateParams);
            SendSmsResponse res = client.SendSms(req);
            log.info("验证码发送成功：phone:[{}], code:[{}]", phone, code);
            log.info("腾讯短信接口返回结果：[{}]", SendSmsResponse.toJsonString(res) );

            valueOperations.set(phone, code, 30, TimeUnit.MINUTES);
        } catch (TencentCloudSDKException e) {
            log.info("验证码发送失败：[{}]", e.getMessage());
        }

        return success();
    }

    public String vcode(){
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
