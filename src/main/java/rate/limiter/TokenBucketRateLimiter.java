package rate.limiter;

import rate.provider.RuleProvider;
import rate.rules.RateLimitRule;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

    private final RuleProvider ruleProvider;
    private final ConcurrentHashMap<String, TokenBucket> tokenBucketMap;

    public TokenBucketRateLimiter(RuleProvider ruleProvider) {
        this.ruleProvider = ruleProvider;
        this.tokenBucketMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean shouldAllow(String namespace, String key) {
        return shouldAllow(namespace, key, 1);
    }

    @Override
    public boolean shouldAllow(String namespace, String key, long requests) {
        final String tokenBucketKey = String.format("%s_%s", namespace, key);
        tokenBucketMap.putIfAbsent(tokenBucketKey, getTokenBucket(namespace, key));
        final TokenBucket tokenBucket = tokenBucketMap.get(tokenBucketKey);
        if (tokenBucket != null) {
            return tokenBucket.shouldAllow(requests);
        }
        return true;
    }

    private TokenBucket getTokenBucket(String namespace, String key) {
        final RateLimitRule rateLimitRule = ruleProvider.getRule(namespace, key);
        return new TokenBucket(rateLimitRule);
    }
}
