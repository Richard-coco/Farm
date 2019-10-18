package cn.farm.Aop;

import cn.farm.Config.Exception.NoLoginException;
import cn.farm.Util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
@Slf4j
public class TestIsLogin {

    @Pointcut("@annotation(cn.farm.Aop.IsLogin)")
    public void isLogin(){}


    @Before("isLogin()")
    public void before(JoinPoint joinPoint)throws NoLoginException {
        Object[] objects = joinPoint.getArgs();
        HttpSession session = (HttpSession) objects[0];
        if(UserUtil.getUserID(session)==-1){
            log.info(joinPoint.getSignature().getName()+" no login");
            throw new NoLoginException();
        }
    }

}
