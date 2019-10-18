package cn.farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.farm.mapper")
@EnableTransactionManagement
public class CommonApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CommonApplication.class);
    }
}
