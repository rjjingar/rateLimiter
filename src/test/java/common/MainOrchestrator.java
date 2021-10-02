package common;

import rate.limiter.RateLimiter;
import rate.provider.RuleProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainOrchestrator {
    public static final String NAME_SPACE = "clientLimits";
    public static final String CLIENT_A = "clientA";
    public static final String CLIENT_B = "clientB";
    public static final String CLIENT_C = "clientC";

    public static void main(String[] args) {
        RuleProvider ruleProvider = RateLimiterTestUtil.getStaticCachedRuleProvider();
        RateLimiter rateLimiter = RateLimiterTestUtil.createTokenBucketRateLimiter(ruleProvider);
        // Since the test prints stats on console we expect time calculations at very low milliseconds to not give accurate results.
        // This assumption might be wrong as well :)

        // Note Under 10ms sleep will not give correct tps calculations.
        // For me 10 ms sleep was giving ~95-98 tps instead of expected 100
        // However 20 ms was very close to expected 50 tps.

        // For testing suggestion is to use > 50ms sleeps e.g. 50ms sleep to simulate 20 tps

        // ClientA limit is 10/sec => 1 req every 100ms and clientA sleeps 100 ms before each call i.e. 10 tps => 0% throttled rate
        TestCaller clientA = new TestCaller(CLIENT_A, rateLimiter, 100);

        // ClientB limit is 10/sec => 1 req every 100ms but clientB sleeps 50 ms before each call i.e. 20 tps => 50% throttled rate
        TestCaller clientB = new TestCaller(CLIENT_B, rateLimiter, 50);

        // ClientC limit is 5/sec => 1 req every 200ms but clientB is calling every 150 ms i.e 6.67 tps => almost 3.34% throttled rate
        TestCaller clientC = new TestCaller(CLIENT_C, rateLimiter, 400);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //executorService.submit(clientA);
        //executorService.submit(clientB);
        executorService.submit(clientC);

    }
}
