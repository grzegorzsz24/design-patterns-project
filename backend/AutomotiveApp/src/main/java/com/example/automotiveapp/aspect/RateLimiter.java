package com.example.automotiveapp.aspect;

class RateLimiter {
    private final int maxCalls;
    private final long duration;
    private int callCount;
    private long windowStart;

    public RateLimiter(int maxCalls, long duration) {
        this.maxCalls = maxCalls;
        this.duration = duration;
        this.windowStart = System.currentTimeMillis();
        this.callCount = 0;
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        if (now - windowStart > duration) {
            windowStart = now;
            callCount = 0;
        }
        if (callCount < maxCalls) {
            callCount++;
            return true;
        }
        return false;
    }
}
