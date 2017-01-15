package com.us.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.*;

/**
 * Created by yangyibo on 17/1/13.
 */

@ComponentScan(basePackages ="com.us.example")
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(Application.class, args);
    }

}
