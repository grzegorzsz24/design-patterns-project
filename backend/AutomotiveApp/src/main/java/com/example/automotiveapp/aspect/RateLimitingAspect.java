package com.example.automotiveapp.aspect;

import com.example.automotiveapp.exception.RateLimitExceededException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

// L8 - third aspect usage
@Aspect
@Component
class RateLimitingAspect {

    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        String key = pjp.getSignature().toShortString();
        RateLimiter limiter = limiters.computeIfAbsent(key,
                k -> new RateLimiter(rateLimit.calls(), rateLimit.duration()));

        if (!limiter.allowRequest()) {
            throw new RateLimitExceededException("Rate limit exceeded for: " + key);
        }
        return pjp.proceed();
    }
}
