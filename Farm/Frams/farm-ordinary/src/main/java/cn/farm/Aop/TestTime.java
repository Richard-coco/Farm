package cn.farm.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestTime {

    public long startTime;

    @Pointcut("@annotation(cn.farm.Aop.CostTime)")
    public void testTime(){

    }

    @Before("testTime()")
    public void before(){
        startTime = System.currentTimeMillis();
    }

    @After("testTime()")
    public void after(JoinPoint joinPoint){
        long endTime = System.currentTimeMillis();
        System.out.println(joinPoint.getSignature().getName()+"cost time :"+(endTime-startTime)+"ms");
    }
}
