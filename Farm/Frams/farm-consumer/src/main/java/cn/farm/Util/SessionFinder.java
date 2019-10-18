package cn.farm.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SessionFinder {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public SessionFinder() {
        log.info("SessionFinder:加载成功");
    }


    //获取redis中的具体服务的JSESSIONID
    public String getSessionID(String jSessionID){
        return stringRedisTemplate.opsForValue().get(jSessionID);
    }


    //通过request从redis获得具体服务的JSESSIONID
    public HttpEntity getRequestEntity(HttpServletRequest request){
        String sessionID = getSessionID(SessionUtil.getJSessionId(request));
        if(sessionID == null){
            log.info("session 过期");
        }
        List<String> cookieList = new ArrayList<>();
        cookieList.add("JSESSIONID="+sessionID);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Cookie",cookieList);
        HttpEntity<String> requestEntity = new HttpEntity<>(null,httpHeaders);
        return requestEntity;
    }
}
