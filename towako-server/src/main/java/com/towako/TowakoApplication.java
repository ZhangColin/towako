package com.towako;

import com.cartisan.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;

/**
 * @author colin
 */
@SpringBootApplication(scanBasePackages = {"com.cartisan", "com.towako"})
@MapperScan("com.towako.**.mapper")
@Slf4j
public class TowakoApplication {
    public static void main(String[] args) {
        /* 在指定的目录下生成应用pid，执行下面的命令关闭应用 :
            'cat /Users/colin/app.pid | xargs kill' */
//        SpringApplication application = new SpringApplication(TowakoApplication.class);
//        application.addListeners(new ApplicationPidFileWriter("/Users/colin/towako.pid"));
//        application.run(args);

        SpringApplication.run(TowakoApplication.class, args);
    }

    @Bean
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(1, 1);
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("Towako application shutdown");
    }
}
