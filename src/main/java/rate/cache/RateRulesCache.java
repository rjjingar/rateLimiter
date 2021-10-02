package rate.cache;

import rate.rules.RateLimitRule;

public interface RateRulesCache {
    RateLimitRule get(String namespace, String key);

    void put(String namespace, String key, RateLimitRule rule);
}
