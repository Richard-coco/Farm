package cn.farm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.farm.expert.mapper")
public class ExpertApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpertApplication.class);
    }
}
