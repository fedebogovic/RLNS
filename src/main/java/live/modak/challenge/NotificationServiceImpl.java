package live.modak.challenge;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

class NotificationServiceImpl implements NotificationService {
    private Gateway gateway;
    private final Map<String, RateLimiter> rateLimiters;

    public NotificationServiceImpl(Gateway gateway, Map<String, RateLimiter> rateLimiters) {
        this.gateway = gateway;
        this.rateLimiters = rateLimiters;
    }

    public NotificationServiceImpl(Gateway gateway) {
        this.gateway = gateway;
        this.rateLimiters = defaultRateLimiters();
    }

    public static Map<String, RateLimiter> defaultRateLimiters() {
        Map<String, RateLimiter> rateLimiters = new HashMap<>();
        rateLimiters.put("status", new RateLimiter(2, Duration.ofMinutes(1)));
        rateLimiters.put("news", new RateLimiter(1, Duration.ofDays(1)));
        rateLimiters.put("marketing", new RateLimiter(3, Duration.ofHours(1)));
        return rateLimiters;
    }

    @Override
    public void send(String type, String userId, String message) {
        RateLimiter rateLimiter = rateLimiters.get(type);
        if (rateLimiter.allowRequest(userId)) {
            gateway.send(userId, message);
        } else {
            gateway.error("Rate limit exceeded for " + type + " notifications to " + userId);
        }
    }
}