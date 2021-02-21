package com.towako.youzan;

import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import com.youzan.cloud.open.sdk.core.oauth.model.OAuthToken;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.cloud.open.sdk.gen.v1_0_0.api.YouzanUserUnionidGet;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUserUnionidGetParams;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUserUnionidGetResult;
import com.youzan.cloud.open.sdk.gen.v1_0_1.api.YouzanScrmCustomerDetailGet;
import com.youzan.cloud.open.sdk.gen.v1_0_1.model.YouzanScrmCustomerDetailGetParams;
import com.youzan.cloud.open.sdk.gen.v1_0_1.model.YouzanScrmCustomerDetailGetResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "有赞")
@RestController
@RequestMapping("/youzan")
@Validated
@Slf4j
public class YouZanController {
    private final YouZanClient youZanClient;

    public YouZanController(YouZanClient youZanClient) {
        this.youZanClient = youZanClient;
    }

    @ApiOperation(value = "获取token")
    @GetMapping("/token")
    public ResponseEntity<OAuthToken> getToken() throws SDKException {
        TokenParameter tokenParameter = TokenParameter.self()
                .clientId("051508c243f3e58c17")
                .clientSecret("11ce048c3bf1b1b0fe6c260a14a6b1be")
                .grantId("92295863")
                .refresh(true)
                .build();
        OAuthToken oAuthToken = youZanClient.getOAuthToken(tokenParameter);

        return success(oAuthToken);
    }


    @ApiOperation(value = "获取有赞OpenId")
    @GetMapping("/yzopenid")
    public ResponseEntity<YouzanUserUnionidGetResult> getYzOpenId(@RequestParam String tokenStr, @RequestParam String unionId) throws SDKException {
        Token token = new Token(tokenStr);

        YouzanUserUnionidGet youzanUserUnionidGet = new YouzanUserUnionidGet();

        YouzanUserUnionidGetParams youzanUserUnionidGetParams = new YouzanUserUnionidGetParams();
        youzanUserUnionidGetParams.setUnionId(unionId);
        youzanUserUnionidGet.setAPIParams(youzanUserUnionidGetParams);

        YouzanUserUnionidGetResult result = youZanClient.invoke(youzanUserUnionidGet, token, YouzanUserUnionidGetResult.class);

        return success(result);
    }


    @ApiOperation(value = "获取有赞OpenId")
    @GetMapping("/customerDetail")
    public ResponseEntity<YouzanScrmCustomerDetailGetResult> getCustomerDetail(@RequestParam String tokenStr, @RequestParam String yzOpenId) throws SDKException {
        Token token = new Token(tokenStr);

        YouzanScrmCustomerDetailGet youzanScrmCustomerDetailGet = new YouzanScrmCustomerDetailGet();

        YouzanScrmCustomerDetailGetParams youzanScrmCustomerDetailGetParams = new YouzanScrmCustomerDetailGetParams();
        youzanScrmCustomerDetailGetParams.setYzOpenId(yzOpenId);
        youzanScrmCustomerDetailGetParams.setFields("user_base");
        youzanScrmCustomerDetailGet.setAPIParams(youzanScrmCustomerDetailGetParams);

        YouzanScrmCustomerDetailGetResult result = youZanClient.invoke(youzanScrmCustomerDetailGet, token, YouzanScrmCustomerDetailGetResult.class);

        return success(result);
    }
}
