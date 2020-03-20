package cn.abel.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * 无数据库运行
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportResource("classpath*:META-INF/spring/*.xml")
public class ShiroRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroRestApplication.class, args);
    }
}
