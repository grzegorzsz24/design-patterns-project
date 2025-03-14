package com.example.automotiveapp.aspect;

import com.example.automotiveapp.logging.Logger;
import com.example.automotiveapp.logging.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
class RetryAspect {
    private static final Logger logger = LoggerFactory.getInstance();

    @Around("@annotation(retryAnnotation)")
    public Object retry(ProceedingJoinPoint pjp, Retry retryAnnotation) throws Throwable {
        int attempts = retryAnnotation.attempts();
        long delay = retryAnnotation.delay();
        Throwable lastException = null;

        for (int i = 0; i < attempts; i++) {
            try {
                return pjp.proceed();
            } catch (Throwable t) {
                lastException = t;
                logger.log("[RETRY] Attempt " + (i + 1) + " failed: " + t.getMessage());
                Thread.sleep(delay);
            }
        }
        if (lastException != null) {
            throw lastException;
        } else {
            throw new IllegalStateException("Retry mechanism: no exception captured, but method did not complete successfully.");
        }
    }
}
