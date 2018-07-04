package com.abel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by yangyibo on 2018/6/27.
 * 服务发现 一个常规的springboot 项目，只需要通过 @EnableEurekaServer 注解开启对EurekaServer 的支持即可
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class,args);
    }
}
