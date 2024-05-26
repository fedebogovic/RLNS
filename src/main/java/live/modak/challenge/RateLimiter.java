package live.modak.challenge;

import java.time.Duration;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class RateLimiter {
    private final int maxRequests;
    private final Duration timeWindow;
    private final Map<String, Deque<Long>> userRequestTimestamps;

    public RateLimiter(int maxRequests, Duration timeWindow) {
        this.maxRequests = maxRequests;
        this.timeWindow = timeWindow;
        this.userRequestTimestamps = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();
        Deque<Long> timestamps = userRequestTimestamps.computeIfAbsent(userId, k -> new LinkedList<>());

        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > timeWindow.toMillis()) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.addLast(now);
            return true;
        } else {
            return false;
        }
    }
}