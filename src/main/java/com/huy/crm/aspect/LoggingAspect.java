package com.huy.crm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.huy.crm.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution(* com.huy.crm.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }

    @Before("forServicePackage()")
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>>> in @Before: calling method: {}", method);

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("=====>>> argument: {}", arg);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>>> in @AfterReturning: from method: {}", method);

        logger.info("=====>>> result: {}", result);
    }
}
