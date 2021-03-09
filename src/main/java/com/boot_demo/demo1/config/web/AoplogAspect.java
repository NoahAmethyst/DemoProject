package com.boot_demo.demo1.config.web;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AoplogAspect {


    @Pointcut("execution(public * com.boot_demo.demo1.controller.*.*(..))")
    public void pointCut() {

    }

//    @Before("pointCut()")
//    public void boBefore(JoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().getName();
//        log.info("Method Name : [" + methodName + "] ---> AOP before ");
//    }
//
//    @After("pointCut()")
//    public void doAfter(JoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().getName();
//        log.info("Method Name : [" + methodName + "] ---> AOP after ");
//    }
//
//    @AfterReturning(pointcut = "pointCut()", returning = "result")
//    public void afterReturn(JoinPoint joinPoint, Object result) {
//        String methodName = joinPoint.getSignature().getName();
//        log.info("Method Name : [" + methodName + "] ---> AOP after return ,and result is : " + result.toString());
//    }
//
//    @AfterThrowing(pointcut = "pointCut()", throwing = "throwable")
//    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
//        String methodName = joinPoint.getSignature().getName();
//        log.info("Method Name : [" + methodName + "] ---> AOP after throwing ,and throwable message is : " + throwable.getMessage());
//    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        try {
            log.info("request class:{} ,interface:{} , params:{}", joinPoint.getTarget().getClass().getName()
                    , joinPoint.getSignature().getName(), JSONObject.toJSONString(joinPoint.getArgs()));
            long startTimeMillis = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            log.info("request class:{} , interface:{}, elapsed time:{}ms,response:{}", joinPoint.getTarget().getClass().getName()
                    , joinPoint.getSignature().getName()
                    , execTimeMillis, JSONObject.toJSONString(result));
            return result;
        } catch (Throwable te) {
            log.error(te.getMessage(), te);
            throw new RuntimeException(te.getMessage());
        }
    }

}
