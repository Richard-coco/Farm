package cn.farm.Config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Myconfig {

    Logger logger = LoggerFactory.getLogger(Myconfig.class);

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        logger.info("restTemplate:加载成功");
        return restTemplate;
    }

    @Bean
    public IRule iRule(){
        logger.info("IRule:加载成功");
        return new BestAvailableRule();
    }


}
