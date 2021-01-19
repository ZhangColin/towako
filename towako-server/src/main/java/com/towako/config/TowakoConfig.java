package com.towako.config;

import com.cartisan.repositories.CartisanRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author colin
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.towako"},
        repositoryFactoryBeanClass = CartisanRepositoryFactoryBean.class)
public class TowakoConfig {
}
