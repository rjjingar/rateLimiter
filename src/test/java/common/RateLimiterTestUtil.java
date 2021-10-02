package common;

import rate.cache.InMemoryLocalCache;
import rate.cache.RateRulesCache;
import rate.limiter.RateLimiter;
import rate.limiter.TokenBucketRateLimiter;
import rate.provider.RuleProvider;
import rate.provider.RulesProviderImpl;
import rate.retriever.RulesRetriever;
import rate.retriever.StaticRulesRetriever;

public class RateLimiterTestUtil {

    private static String RULE_FILE = "/home/rohan/work/rateLimiter/src/test/java/common/LimitRules.cfg";
    public static RuleProvider getStaticCachedRuleProvider() {

        RateRulesCache cache = new InMemoryLocalCache();
        RulesRetriever retriever = new StaticRulesRetriever(RULE_FILE);
        return new RulesProviderImpl(cache, retriever);
    }

    public static RateLimiter createTokenBucketRateLimiter(RuleProvider ruleProvider) {
        return new TokenBucketRateLimiter(ruleProvider);
    }
}
