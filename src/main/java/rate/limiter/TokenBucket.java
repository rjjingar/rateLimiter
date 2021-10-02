package rate.limiter;

import rate.rules.RateLimitRule;

import java.util.concurrent.TimeUnit;

public class TokenBucket {
    private static long MICRO_ONE_SEC = TimeUnit.MICROSECONDS.convert(1, TimeUnit.SECONDS);
    private final RateLimitRule rule;
    private long currTokens;
    private long lastRefreshNanoSeconds;
    private double tokensPerMicroSecond;

    public TokenBucket(RateLimitRule rule) {
        this.rule = rule;
        tokensPerMicroSecond = rule.getTotalLimit() / MICRO_ONE_SEC;
    }

    public synchronized boolean shouldAllow(long tokens) {
        refill();
        if (currTokens >= tokens) {
            currTokens -= tokens;
            return true;
        }
        return false;
    }

    public void refill() {
        final long now = System.nanoTime();
        final long timeDelta = now - lastRefreshNanoSeconds;
        final long timeElapsed = TimeUnit.MICROSECONDS.convert(timeDelta, TimeUnit.NANOSECONDS);
        final double tokensToAdd =  tokensPerMicroSecond * timeElapsed;
        System.out.println("Tokens to add " + tokensToAdd + " timeElapsed " + timeElapsed + " timeDelta " + timeDelta + " tokensPerMicroSecond " + tokensPerMicroSecond);
        if (tokensToAdd < 1.0) return;
        currTokens = (long) Math.min(rule.getTotalLimit(), tokensToAdd + currTokens);
        lastRefreshNanoSeconds = now;
    }
}
