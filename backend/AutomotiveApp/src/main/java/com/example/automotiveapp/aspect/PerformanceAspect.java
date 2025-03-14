package com.example.automotiveapp.aspect;

import com.example.automotiveapp.logging.Logger;
import com.example.automotiveapp.logging.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// L8 - second aspect usage
@Component
@Aspect
class PerformanceAspect {
    private static final Logger logger = LoggerFactory.getInstance();

    @Around("execution(* com.example.automotiveapp.service.search.*.*(..))")
    Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        logger.log("[PERFORMANCE] Method " + joinPoint.getSignature().getName() + " executed in " + end + " ms");

        return result;
    }
}
