package com.towako.system.common;

import com.cartisan.aops.limit.Limit;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/limit")
@Api(tags = "系统：限流测试管理")
@Slf4j
public class LimitController {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @GetMapping
    @Limit(key = "test", period = 60, count = 10, name = "testLimit", prefix = "limit")
    public int testLimit() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
