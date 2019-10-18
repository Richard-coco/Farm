package cn.farm.Aop;

import cn.farm.Util.SessionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class JSessionDispenser {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Pointcut("@annotation(cn.farm.Aop.SessionDispenser)")
    public void sessionDispenser(){
    }

    /*
    验证请求是否在redis中有对应的jsessionid如果没有ping通对应服务将生成jsessionid的k-v对存入redis
     */
    @Before("sessionDispenser()")
    public void before(JoinPoint joinPoint){

        Object[] objects = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) objects[0];
        //consumer JSESSIONID
        String consumerId = null;
        //具体服务的JSESSIONID
        String sessionId = null;

        consumerId = SessionUtil.getJSessionId(request);
        if(consumerId != null){
            sessionId = stringRedisTemplate.opsForValue().get(consumerId);
            if(sessionId == null){
                ResponseEntity<JSONObject> responseEntity;
                responseEntity = restTemplate.getForEntity("http://farm-"+objects[1]+"/farm/"+objects[1]+"/ping", JSONObject.class);
                String first = responseEntity.getHeaders().getFirst("Set-Cookie");
                String[] strings = first.split("=");
                String[] targetS = strings[1].split(";");
                sessionId = targetS[0];
                stringRedisTemplate.opsForValue().set(consumerId,sessionId,60*30,TimeUnit.SECONDS);
                objects[2] = sessionId;
            }else{
                sessionId = stringRedisTemplate.opsForValue().get(consumerId);
            }
        }else{
            throw new RuntimeException();
        }

        System.out.println(consumerId+"--------------------"+sessionId);
    }
}
