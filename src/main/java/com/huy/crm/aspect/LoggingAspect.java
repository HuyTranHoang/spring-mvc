package com.huy.crm.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

        private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

        @Pointcut("execution(* com.huy.crm.service.*.*(..))")
        private void forServicePackage() {
        }

        @Pointcut("execution(* com.huy.crm.dao.*.*(..))")
        private void forDaoPackage() {
        }

        @Pointcut("forServicePackage() || forDaoPackage()")
        private void forAppFlow() {
        }

        @Before("forAppFlow()")
        public void before(JoinPoint joinPoint) {
            String method = joinPoint.getSignature().toShortString();
            logger.info("=====>>> in @Before: calling method: " + method);

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                logger.info("=====>>> argument: " + arg);
            }
        }
}
