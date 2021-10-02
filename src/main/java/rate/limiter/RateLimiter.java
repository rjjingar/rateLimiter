package rate.limiter;

public interface RateLimiter {

    boolean shouldAllow(String namespace, String key);

    boolean shouldAllow(String namespace, String key, long requests);
}
