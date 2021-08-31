package com.towako.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.towako.vip.membership.MembershipAppService;
import com.towako.vip.membership.response.MembershipDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * 微信小程序用户接口
 *
 * @author colin
 */
@RestController
@RequestMapping("/ma/user")
@Slf4j
@AllArgsConstructor
public class WxMaUserController {
    private final WxMaService wxMaService;
    private final MembershipAppService membershipAppService;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(String code) {
        if (StringUtils.isBlank(code)) {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("empty jscode"));
        }

        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            final String openId = session.getOpenid();

            log.info("Session key: [{}]", session.getSessionKey());
            log.info("OpenId: [{}]", openId);

            final String appId = wxMaService.getWxMaConfig().getAppid();

            final Optional<MembershipDto> userOptional = membershipAppService.findByOpenId(appId, openId);

            userOptional.ifPresent(userDto -> membershipAppService.recordLogin(userDto.getId()));

            Map<Object, Object> result = new HashMap<>();
            result.put("token", session.getSessionKey());
            result.put("tokenExpire", LocalDate.now().plusDays(1).toString());
            result.put("sessionKey", session.getSessionKey());
            result.put("userInfo", userOptional.orElse(null));
            result.put("openId", openId);

            return success(result);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            log.error("微信登录,调用官方接口失败：{}", code);
            throw new CartisanException(CodeMessage.FAIL.fillArgs(e.getMessage()));
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/register")
    public ResponseEntity<MembershipDto> info(String sessionKey,
                                        String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("user check failed"));
        }

        // 解密用户信息
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
//        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        final String appId = wxMaService.getWxMaConfig().getAppid();

//        String unionId = wxMaUserInfo.getUnionId();
        String unionId = wxMaUserInfo.getNickName();
        if (unionId == null) {
//            unionId="mock-"+UUID.randomUUID();
            unionId="mock";
        }
//        final MembershipDto membershipDto = membershipAppService.registerByMiniApp(appId, wxMaUserInfo.getOpenId(), unionId,
//                "", wxMaUserInfo.getNickName(), wxMaUserInfo.getAvatarUrl(), Integer.parseInt(wxMaUserInfo.getGender()),
//                wxMaUserInfo.getCity(), wxMaUserInfo.getProvince(), wxMaUserInfo.getCountry());

        final MembershipDto membershipDto = membershipAppService.registerByMiniApp(appId, wxMaUserInfo.getNickName(), unionId,
                "", wxMaUserInfo.getNickName(), wxMaUserInfo.getAvatarUrl(), Integer.parseInt(wxMaUserInfo.getGender()),
                wxMaUserInfo.getCity(), wxMaUserInfo.getProvince(), wxMaUserInfo.getCountry());

        membershipAppService.recordLogin(membershipDto.getId());


        return success(membershipDto);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public ResponseEntity<WxMaPhoneNumberInfo> phone(String sessionKey, String signature,
                                                     String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("user check failed"));
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return success(phoneNoInfo);
    }

}
