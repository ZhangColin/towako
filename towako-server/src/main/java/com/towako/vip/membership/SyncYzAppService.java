package com.towako.vip.membership;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.towako.vip.wechatmembership.WechatMembershipAppService;
import com.youzan.cloud.open.sdk.common.exception.SDKException;
import com.youzan.cloud.open.sdk.core.client.auth.Token;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import com.youzan.cloud.open.sdk.core.oauth.model.OAuthToken;
import com.youzan.cloud.open.sdk.core.oauth.token.TokenParameter;
import com.youzan.cloud.open.sdk.gen.v1_0_0.api.YouzanUserUnionidGet;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUserUnionidGetParams;
import com.youzan.cloud.open.sdk.gen.v1_0_0.model.YouzanUserUnionidGetResult;
import com.youzan.cloud.open.sdk.gen.v1_0_1.api.YouzanScrmCustomerDetailGet;
import com.youzan.cloud.open.sdk.gen.v1_0_1.model.YouzanScrmCustomerDetailGetParams;
import com.youzan.cloud.open.sdk.gen.v1_0_1.model.YouzanScrmCustomerDetailGetResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author colin
 */
@Service
@Slf4j
public class SyncYzAppService {
    private final YouZanClient youZanClient;
    private final MembershipRepository membershipRepository;
    private final WechatMembershipAppService wechatMembershipAppService;
    private final ObjectMapper objectMapper;

    public SyncYzAppService(YouZanClient youZanClient, MembershipRepository membershipRepository,
                            WechatMembershipAppService wechatMembershipAppService, ObjectMapper objectMapper) {
        this.youZanClient = youZanClient;
        this.membershipRepository = membershipRepository;
        this.wechatMembershipAppService = wechatMembershipAppService;
        this.objectMapper = objectMapper;
    }

    @Transactional(rollbackOn = Exception.class)
    public void syncYzData() {
        try {
            final Token token = getYzToken();

            membershipRepository.findAll().stream().filter(membership -> StringUtils.isEmpty(membership.getPhone()))
                    .forEach(membership -> {
                        try {
                            final String unionId = wechatMembershipAppService.getUnionId(membership.getId());

                            final Optional<String> yzOpenId = getYzOpenId(token, unionId);

                            if (yzOpenId.isPresent()) {
                                final String mobile = getMobile(token, yzOpenId.get());

                                membership.setPhone(mobile);
                                membershipRepository.save(membership);
                            }
                        }
                        catch (SDKException sdkException){
                            log.error("从有赞获取手机号失败", sdkException);
                        }
                    });
        }
        catch (SDKException sdkEx){
            log.error("获取token失败", sdkEx);
        }
    }


    private Token getYzToken() throws SDKException {
        TokenParameter tokenParameter = TokenParameter.self()
                .clientId("051508c243f3e58c17")
                .clientSecret("11ce048c3bf1b1b0fe6c260a14a6b1be")
                .grantId("92295863")
                .refresh(false)
                .build();
        OAuthToken oAuthToken = youZanClient.getOAuthToken(tokenParameter);

        String resultString = null;
        try {
            resultString = objectMapper.writeValueAsString(oAuthToken);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.info("获取有赞token: [{}], 完整结果：[{}]", oAuthToken.getAccessToken(), resultString);

        return new Token(oAuthToken.getAccessToken());
    }

    public Optional<String> getYzOpenId(Token token, String unionId) throws SDKException {
        log.info("使用token[{}]获取微信unionId[{}]对应的YzOpenId", token.getAccessToken(), unionId);
        YouzanUserUnionidGet youzanUserUnionidGet = new YouzanUserUnionidGet();

        YouzanUserUnionidGetParams youzanUserUnionidGetParams = new YouzanUserUnionidGetParams();
        youzanUserUnionidGetParams.setUnionId(unionId);
        youzanUserUnionidGet.setAPIParams(youzanUserUnionidGetParams);

        YouzanUserUnionidGetResult result = youZanClient.invoke(youzanUserUnionidGet, token, YouzanUserUnionidGetResult.class);

        final Optional<String> yzOpenId = Optional.ofNullable(result.getData()).map(YouzanUserUnionidGetResult.YouzanUserUnionidGetResultData::getYzOpenId);

        String resultString = null;
        try {
            resultString = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        log.info("使用token[{}]获取微信unionId[{}]对应的YzOpenId，YzOpenId: [{}], 完整结果: [{}]", token.getAccessToken(), unionId, yzOpenId, resultString);

        return yzOpenId;
    }


    public String getMobile(Token token, @RequestParam String yzOpenId) throws SDKException {
        log.info("使用token[{}]获取YzOpenId[{}]对应的手机号", token.getAccessToken(), yzOpenId);
        YouzanScrmCustomerDetailGet youzanScrmCustomerDetailGet = new YouzanScrmCustomerDetailGet();

        YouzanScrmCustomerDetailGetParams youzanScrmCustomerDetailGetParams = new YouzanScrmCustomerDetailGetParams();
        youzanScrmCustomerDetailGetParams.setYzOpenId(yzOpenId);
        youzanScrmCustomerDetailGetParams.setFields("user_base");
        youzanScrmCustomerDetailGet.setAPIParams(youzanScrmCustomerDetailGetParams);

        YouzanScrmCustomerDetailGetResult result = youZanClient.invoke(youzanScrmCustomerDetailGet, token, YouzanScrmCustomerDetailGetResult.class);

        String resultString = null;
        try {
            resultString = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        final String mobile = result.getData().getMobile();
        log.info("使用token[{}]获取YzOpenId[{}]对应的手机号，手机号: [{}]，完整结果: [{}]", token.getAccessToken(), yzOpenId, mobile, resultString);
        return mobile;
    }
}
