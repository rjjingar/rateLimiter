package rate.provider;

import rate.rules.RateLimitRule;

import java.util.Map;

public interface RuleProvider {

    public RateLimitRule getRule(String namespace, String key);
    Map<String, RateLimitRule> getAllRules(String namespace);
    Map<String, Map<String, RateLimitRule>> getAllRules();

}
