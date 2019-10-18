package cn.farm;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("cn.farm.mapper")
@SpringBootApplication
@EnableEurekaClient
public class SysUserApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SysUserApplication.class);
    }
}
