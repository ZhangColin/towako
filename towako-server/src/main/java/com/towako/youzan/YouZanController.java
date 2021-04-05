package com.towako.youzan;

import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import com.youzan.cloud.open.sdk.core.oauth.model.OAuthToken;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.cloud.open.sdk.gen.v1_0_0.api.YouzanScrmCustomerList;
import com.youzan.cloud.open.sdk.gen.v1_0_0.api.YouzanUsersInfoQuery;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanScrmCustomerListParams;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanScrmCustomerListResult;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUsersInfoQueryParams;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUsersInfoQueryResult;
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


    @ApiOperation(value = "获取有赞用户信息")
    @GetMapping("/yzuserinfo")
    public ResponseEntity<YouzanUsersInfoQueryResult> getYzUserInfo(@RequestParam String tokenStr, @RequestParam String unionId) throws SDKException {
        Token token = new Token(tokenStr);

        final YouzanUsersInfoQuery youzanUsersInfoQuery = new YouzanUsersInfoQuery();

        final YouzanUsersInfoQueryParams youzanUsersInfoQueryParams = new YouzanUsersInfoQueryParams();
        youzanUsersInfoQueryParams.setWeixinUnionId(unionId);
        youzanUsersInfoQuery.setAPIParams(youzanUsersInfoQueryParams);

        YouzanUsersInfoQueryResult result = youZanClient.invoke(youzanUsersInfoQuery, token, YouzanUsersInfoQueryResult.class);

        return success(result);
    }

    @ApiOperation(value = "获取有赞用户信息")
    @GetMapping("/yzuserinfo1")
    public ResponseEntity<YouzanUsersInfoQueryResult> getYzUserInfo1(@RequestParam String tokenStr, @RequestParam String yzOpenId) throws SDKException {
        Token token = new Token(tokenStr);

        final YouzanUsersInfoQuery youzanUsersInfoQuery = new YouzanUsersInfoQuery();

        final YouzanUsersInfoQueryParams youzanUsersInfoQueryParams = new YouzanUsersInfoQueryParams();
        youzanUsersInfoQueryParams.setYzOpenId(yzOpenId);
        youzanUsersInfoQuery.setAPIParams(youzanUsersInfoQueryParams);

        YouzanUsersInfoQueryResult result = youZanClient.invoke(youzanUsersInfoQuery, token, YouzanUsersInfoQueryResult.class);

        return success(result);
    }


//    @ApiOperation(value = "获取有赞客户详情")
//    @GetMapping("/customerDetail")
//    public ResponseEntity<YouzanScrmCustomerDetailGetResult> getCustomerDetail(@RequestParam String tokenStr, @RequestParam String yzOpenId) throws SDKException {
//        Token token = new Token(tokenStr);
//
//        YouzanScrmCustomerDetailGet youzanScrmCustomerDetailGet = new YouzanScrmCustomerDetailGet();
//
//        YouzanScrmCustomerDetailGetParams youzanScrmCustomerDetailGetParams = new YouzanScrmCustomerDetailGetParams();
//        youzanScrmCustomerDetailGetParams.setYzOpenId(yzOpenId);
//        youzanScrmCustomerDetailGetParams.setFields("user_base");
//        youzanScrmCustomerDetailGet.setAPIParams(youzanScrmCustomerDetailGetParams);
//
//        YouzanScrmCustomerDetailGetResult result = youZanClient.invoke(youzanScrmCustomerDetailGet, token, YouzanScrmCustomerDetailGetResult.class);
//
//        return success(result);
//    }

    @ApiOperation(value = "获取有赞客户列表")
    @GetMapping("/customerList")
    public ResponseEntity<YouzanScrmCustomerListResult> getCustomerList(@RequestParam String tokenStr) throws SDKException {
        Token token = new Token(tokenStr);

        final YouzanScrmCustomerList youzanScrmCustomerList = new YouzanScrmCustomerList();

        final YouzanScrmCustomerListParams youzanScrmCustomerListParams = new YouzanScrmCustomerListParams();
        youzanScrmCustomerListParams.setPageNo(1);
        youzanScrmCustomerListParams.setPageSize(50);
        youzanScrmCustomerList.setAPIParams(youzanScrmCustomerListParams);

        final YouzanScrmCustomerListResult result = youZanClient.invoke(youzanScrmCustomerList, token, YouzanScrmCustomerListResult.class);

        return success(result);
    }
}
