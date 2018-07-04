package com.abel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by yangyibo on 2018/6/29.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //开启fegin 客户端支持
@EnableCircuitBreaker //开启CircuitBreaker的支持
@EnableZuulProxy //开启网关代理支持
public class UiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
