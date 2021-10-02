package rate.retriever;

import rate.rules.RateLimitRule;

import java.util.Map;

public interface RulesRetriever {
    RateLimitRule retrieve(String namespace, String key);


    Map<String, RateLimitRule> retrieveAllRules(String namespace);
    Map<String, Map<String, RateLimitRule>> retrieveAllRules();
}
