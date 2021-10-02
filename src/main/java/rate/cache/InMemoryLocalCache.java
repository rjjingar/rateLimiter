package rate.cache;

import rate.rules.RateLimitRule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLocalCache implements RateRulesCache {
    private final ConcurrentHashMap<String, Map<String, RateLimitRule>> cache;

    public InMemoryLocalCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public RateLimitRule get(String namespace, String key) {
        if (cache.containsKey(namespace) && cache.get(namespace).containsKey(key)) {
            return cache.get(namespace).get(key);
        }
        return null;
    }

    @Override
    public void put(String namespace, String key, RateLimitRule rule) {
        cache.putIfAbsent(namespace, new ConcurrentHashMap<>());
        cache.get(namespace).put(key, rule);
    }

}
