package com.towako.wx.mp.controller;

import com.cartisan.security.LoginService;
import com.cartisan.utils.AesUtil;
import com.towako.assistedreproduction.medicalrecord.AddMedicalRecordByMembership;
import com.towako.assistedreproduction.medicalrecord.MedicalRecordAppService;
import com.towako.system.user.application.LoginAppService;
import com.towako.traffic.channel.ChannelRepository;
import com.towako.traffic.recommend.RecommendAppService;
import com.towako.vip.membership.MembershipAppService;
import com.towako.vip.membership.response.MembershipDto;
import com.towako.vip.wechateventrecord.WechatEventRecordAppService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author colin
 */
@AllArgsConstructor
@RestController
@RequestMapping("/mp")
@Slf4j
public class WxMpLoginController {
    private final WxMpService wxMpService;
    private final MembershipAppService membershipAppService;
    private final RecommendAppService recommendAppService;
    private final LoginService loginService;
    private final MedicalRecordAppService medicalRecordAppService;

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam String code) throws WxErrorException {
//        if (!this.wxService.switchover(appid)) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
//        }
        WxMpOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        WxMpUser user = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);

        if (user != null) {
            final String appId = wxMpService.getWxMpConfigStorage().getAppId();

            final Optional<MembershipDto> userOptional = membershipAppService.findByOpenId(appId, user.getOpenId());

            MembershipDto membershipDto;

            if (!userOptional.isPresent()) {
                String unionId = user.getUnionId();
                if (unionId == null) {
                    // 没有开通第三方公众平台时，使用openId来替代
                    unionId = user.getOpenId();
                }

                membershipDto = membershipAppService.registerByWechat(appId, user.getOpenId(), unionId,
                        "", user.getNickname(), user.getHeadImgUrl(), user.getSex(),
                        user.getCity(), user.getProvince(), user.getCountry(), "");

                membershipAppService.recordLogin(membershipDto.getId());

                recommendAppService.recommend("1522824674277462016", membershipDto.getId(), membershipDto.getNickname());
            }
            else {
                membershipAppService.recordLogin(userOptional.get().getId());
                membershipDto = userOptional.get();
            }

            final AddMedicalRecordByMembership addMedicalRecordByMembership = new AddMedicalRecordByMembership();
            addMedicalRecordByMembership.setMemberId(membershipDto.getId());
            addMedicalRecordByMembership.setName(membershipDto.getNickname());
            addMedicalRecordByMembership.setPhone(membershipDto.getPhone());
            addMedicalRecordByMembership.setBirthday(membershipDto.getBirthday());
            medicalRecordAppService.addMedicalRecord(addMedicalRecordByMembership);

            final String token = loginService.login("WechatMember_" + membershipDto.getId(), "L@nmedic@1");

            HashMap<String, Object> data = new HashMap<>();
            data.put("userInfo", membershipDto);
            data.put("accessToken", token);

            return data;
        }

        return null;
    }
}
