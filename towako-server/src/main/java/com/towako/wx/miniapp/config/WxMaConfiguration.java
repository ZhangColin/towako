package com.towako.wx.miniapp.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.towako.wx.miniapp.handler.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.stream.Collectors.toMap;

/**
 * @author colin
 */
@Configuration
@EnableConfigurationProperties(WxMiniAppProperties.class)
@AllArgsConstructor
public class WxMaConfiguration {
    private final WxMiniAppProperties properties;

    private final MaLogHandler logHandler;
    private final MaSubscribeHandler subscribeHandler;
    private final MaTextHandler textHandler;
    private final MaPictureHandler pictureHandler;
    private final MaQrcodeHandler qrcodeHandler;

    @Bean
    public WxMaService wxMaService() {
        List<WxMiniAppProperties.MiniAppConfig> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("微信小程序配置错误"));
        }

        WxMaService service = new WxMaServiceImpl();
        service.setMultiConfigs(configs.stream().map(config->{
            WxMaDefaultConfigImpl configStorage = new WxMaDefaultConfigImpl();
            configStorage.setAppid(config.getAppId());
            configStorage.setSecret(config.getSecret());
            configStorage.setToken(config.getToken());
            configStorage.setAesKey(config.getAesKey());
            configStorage.setMsgDataFormat(config.getMsgDataFormat());

            return configStorage;
        }).collect(toMap(WxMaDefaultConfigImpl::getAppid, a->a, (o,n)->o)));

        return service;
    }

    @Bean
    public WxMaMessageRouter wxMaMessageRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router
                .rule().handler(logHandler).next()
                .rule().async(false).content("订阅消息").handler(subscribeHandler).end()
                .rule().async(false).content("文本").handler(textHandler).end()
                .rule().async(false).content("图片").handler(pictureHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }

}

