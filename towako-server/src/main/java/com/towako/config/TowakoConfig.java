package com.towako.config;

import com.cartisan.repositories.CartisanRepositoryFactoryBean;
import com.youzan.cloud.open.sdk.core.client.core.DefaultYZClient;
import com.youzan.cloud.open.sdk.core.client.core.YouZanClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author colin
 */
@Configuration
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.towako"},
        repositoryFactoryBeanClass = CartisanRepositoryFactoryBean.class)
public class TowakoConfig {
    @Bean
    public YouZanClient youZanClient() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(5, TimeUnit.HOURS);
//        HttpConfig httpConfig = HttpConfig.builder().OkHttpClientBuilder(builder).build();
//
//        YouZanClient youZanClient = new DefaultYZClient(httpConfig);

        return new DefaultYZClient();
    }
}
