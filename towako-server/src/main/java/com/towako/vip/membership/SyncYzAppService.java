package com.towako.vip.membership;

import com.cartisan.utils.SnowflakeIdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.towako.traffic.recommend.Recommend;
import com.towako.traffic.recommend.RecommendRepository;
import com.towako.vip.membership.domain.Gender;
import com.towako.vip.membership.domain.Membership;
import com.towako.vip.membership.mapper.MembershipRecommendMapper;
import com.towako.vip.wechatmembership.WechatMembership;
import com.towako.vip.wechatmembership.WechatMembershipAppService;
import com.towako.vip.wechatmembership.WechatMembershipRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static java.util.stream.Collectors.toList;

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
    private final SnowflakeIdWorker idWorker;
    private final RecommendRepository recommendRepository;
    private final MembershipRecommendMapper membershipRecommendMapper;
    private final WechatMembershipRepository wechatMembershipRepository;

    public SyncYzAppService(YouZanClient youZanClient, MembershipRepository membershipRepository,
                            WechatMembershipAppService wechatMembershipAppService, ObjectMapper objectMapper,
                            SnowflakeIdWorker idWorker, RecommendRepository recommendRepository,
                            MembershipRecommendMapper membershipRecommendMapper,
                            WechatMembershipRepository wechatMembershipRepository) {
        this.youZanClient = youZanClient;
        this.membershipRepository = membershipRepository;
        this.wechatMembershipAppService = wechatMembershipAppService;
        this.objectMapper = objectMapper;
        this.idWorker = idWorker;
        this.recommendRepository = recommendRepository;
        this.membershipRecommendMapper = membershipRecommendMapper;
        this.wechatMembershipRepository = wechatMembershipRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    @Scheduled(cron = "0 0 3 ? * *")
    public void syncYzMobile() {
        try {
            final Token token = getYzToken();

            membershipRepository.findAll().stream().filter(membership -> StringUtils.isEmpty(membership.getPhone()))
                    .forEach(membership -> {
                        try {
                            final String unionId = wechatMembershipAppService.getUnionId(membership.getId());


                            final String mobile = getMobile(token, unionId);

                            membership.setPhone(mobile);
                            membershipRepository.save(membership);
                        } catch (SDKException sdkException) {
                            log.error("从有赞获取手机号失败", sdkException);
                        }
                    });
        } catch (SDKException sdkEx) {
            log.error("获取token失败", sdkEx);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Scheduled(cron = "0 30 3 ? * *")
    public void syncYzVip() {
        try {
            final Token token = getYzToken();

            final List<YouzanScrmCustomerListResult.YouzanScrmCustomerListResultRecordlist> youzanCustomerList =
                    getYouzanCustomerList(token);

            final List<String> existPhones = membershipRepository.findAll().stream().map(Membership::getPhone)
                    .filter(phone -> !StringUtils.isEmpty(phone)).collect(toList());

            youzanCustomerList.stream().filter(c -> !StringUtils.isEmpty(c.getMobile()) && !existPhones.contains(c.getMobile()))
                    .forEach(c -> {
                        try {
                            final YouzanUsersInfoQuery youzanUsersInfoQuery = new YouzanUsersInfoQuery();

                            final YouzanUsersInfoQueryParams youzanUsersInfoQueryParams = new YouzanUsersInfoQueryParams();
                            youzanUsersInfoQueryParams.setYzOpenId(c.getYzOpenId());
                            youzanUsersInfoQuery.setAPIParams(youzanUsersInfoQueryParams);

                            YouzanUsersInfoQueryResult result = youZanClient.invoke(youzanUsersInfoQuery, token, YouzanUsersInfoQueryResult.class);

                            final List<YouzanUsersInfoQueryResult.YouzanUsersInfoQueryResultUserlist> userInfoList = result.getData().getUserList();

                            if (userInfoList.size()>0){
                                final YouzanUsersInfoQueryResult.YouzanUsersInfoQueryResultUserlist userInfo = userInfoList.get(0);
                                log.info("创建有赞用户：[{},{}]", userInfo.getPrimitiveInfo().getNickName(), userInfo.getMobileInfo().getMobile());
                                final long memberId = idWorker.nextId();
                                final Membership membership = Membership.createByWechat(memberId, userInfo.getMobileInfo().getMobile(),
                                        userInfo.getPrimitiveInfo().getNickName(), userInfo.getPrimitiveInfo().getAvatar(),
                                        Gender.getInstance(userInfo.getPrimitiveInfo().getGender()), null,
                                        userInfo.getPrimitiveInfo().getCity(), userInfo.getPrimitiveInfo().getProvince(),
                                        userInfo.getPrimitiveInfo().getCountry());
                                membershipRepository.save(membership);

                                final Recommend recommend = new Recommend(1522824674277462016L, memberId,
                                        userInfo.getPrimitiveInfo().getNickName());

                                recommendRepository.save(recommend);
                            }

                        } catch (SDKException sdkException) {
                            log.error("获取用户信息失败", sdkException);
                        }
                    });

        } catch (SDKException sdkEx) {
            log.error("获取token失败", sdkEx);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Scheduled(cron = "0 0 4 ? * *")
    public void removeDuplicateUser() {
        this.membershipRecommendMapper.findDuplicatePhone().forEach(phone->{
            final MembershipQuery membershipQuery = new MembershipQuery();
            membershipQuery.setPhone(phone);
            final List<Membership> memberships = membershipRepository.findAll(querySpecification(membershipQuery));

            if (memberships.size()==2){
                final List<Long> memberIds = memberships.stream().map(Membership::getId).collect(toList());
                final Optional<Long> wechatMemberIdOptional = wechatMembershipRepository.findByMemberIdIn(memberIds).stream()
                        .map(WechatMembership::getMemberId).findFirst();
                if (wechatMemberIdOptional.isPresent()){
                    final Long wechatMemberId = wechatMemberIdOptional.get();
                    final Long youZanMemberId = memberIds.stream().filter(id -> !id.equals(wechatMemberId)).findFirst().get();

                    final Membership wechatMembership = memberships.stream().filter(membership -> membership.getId().equals(wechatMemberId)).findFirst().get();
                    final Membership youZanMembership = memberships.stream().filter(membership -> membership.getId().equals(youZanMemberId)).findFirst().get();

                    final Recommend recommend = recommendRepository.findByMemberId(youZanMemberId);
                    recommend.setMemberId(wechatMemberId);
                    recommend.setNickName(wechatMembership.getNickname());
                    recommendRepository.save(recommend);

                    this.membershipRecommendMapper.updateMembershipCreateDate(wechatMembership.getId(), youZanMembership.getCreateDateTime());

                    membershipRepository.delete(youZanMembership);
                }
            }

        });
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

    public String getMobile(Token token, @RequestParam String unionId) throws SDKException {
        log.info("使用token[{}]获取unionId[{}]对应的手机号", token.getAccessToken(), unionId);
        final YouzanUsersInfoQuery youzanUsersInfoQuery = new YouzanUsersInfoQuery();

        final YouzanUsersInfoQueryParams youzanUsersInfoQueryParams = new YouzanUsersInfoQueryParams();
        youzanUsersInfoQueryParams.setWeixinUnionId(unionId);
        youzanUsersInfoQuery.setAPIParams(youzanUsersInfoQueryParams);

        YouzanUsersInfoQueryResult result = youZanClient.invoke(youzanUsersInfoQuery, token, YouzanUsersInfoQueryResult.class);

        final List<YouzanUsersInfoQueryResult.YouzanUsersInfoQueryResultUserlist> userInfoList = result.getData().getUserList();

        String mobile = "";
        if (userInfoList.size() > 0) {
            mobile = userInfoList.get(0).getMobileInfo().getMobile();
        }

        log.info("使用token[{}] unionId[{}]对应的手机号，手机号: [{}]", token.getAccessToken(), unionId, mobile);
        return mobile;
    }

    public List<YouzanScrmCustomerListResult.YouzanScrmCustomerListResultRecordlist> getYouzanCustomerList(Token token) throws SDKException {
        log.info("使用token[{}]获取有赞客户列表", token.getAccessToken());
        final YouzanScrmCustomerList youzanScrmCustomerList = new YouzanScrmCustomerList();

        int pageNo = 1;
        long pageTotal = -1L;

        final List<YouzanScrmCustomerListResult.YouzanScrmCustomerListResultRecordlist> recordList = new ArrayList<>();

        do {
            final YouzanScrmCustomerListParams youzanScrmCustomerListParams = new YouzanScrmCustomerListParams();
            youzanScrmCustomerListParams.setPageNo(pageNo);
            youzanScrmCustomerListParams.setPageSize(50);
            youzanScrmCustomerList.setAPIParams(youzanScrmCustomerListParams);

            final YouzanScrmCustomerListResult result = youZanClient.invoke(youzanScrmCustomerList, token, YouzanScrmCustomerListResult.class);

            recordList.addAll(result.getData().getRecordList());

            if (pageTotal == -1) {
                pageTotal = calPageTotal(result.getData().getTotal());
            }
            pageNo++;
        } while (pageNo < pageTotal);

        log.info("使用token[{}]获取用户[{}]个", token.getAccessToken(), recordList.size());
        return recordList;
    }

    private Long calPageTotal(Long total) {
        Long pageTotal;
        if (total % 50 > 0) {
            pageTotal = total / 50 + 1;
        } else {
            pageTotal = total / 50;
        }
        return pageTotal;
    }
}
