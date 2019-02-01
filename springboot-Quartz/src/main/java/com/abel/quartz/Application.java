package com.abel.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by yangyibo on 2019/2/1.
 */
@SpringBootApplication
@ImportResource("classpath*:META-INF/spring/*.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
