package rate.provider;

import lombok.RequiredArgsConstructor;
import rate.cache.RateRulesCache;
import rate.retriever.RulesRetriever;
import rate.rules.RateLimitRule;

import java.util.Map;

@RequiredArgsConstructor
public class RulesProviderImpl implements RuleProvider {

    private final RateRulesCache cache;
    private final RulesRetriever retriever;
    
    @Override
    public RateLimitRule getRule(String namespace, String key) {
        RateLimitRule rule = cache.get(namespace, key);
        if (rule == null) {
            rule = retriever.retrieve(namespace, key);
            if (rule != null) {
                cache.put(namespace, key, rule);
            }
        }
        return rule;
    }

    @Override
    public Map<String, RateLimitRule> getAllRules(String namespace) {
        final Map<String, RateLimitRule> ruleMap = retriever.retrieveAllRules(namespace);
        for (Map.Entry<String, RateLimitRule> e : ruleMap.entrySet()) {
            cache.put(namespace, e.getKey(), e.getValue());
        }
        return ruleMap;
    }

    @Override
    public Map<String, Map<String, RateLimitRule>> getAllRules() {
        final Map<String, Map<String, RateLimitRule>> ruleMap = retriever.retrieveAllRules();
        for (Map.Entry<String, Map<String, RateLimitRule>> e : ruleMap.entrySet()) {
            for (Map.Entry<String, RateLimitRule> e2 : e.getValue().entrySet()) {
                cache.put(e.getKey(), e2.getKey(), e2.getValue());
            }
        }
        return ruleMap;
    }
}
