public class RateLimitRequest {
    private final String userId;
    private final int timestamp;

    public RateLimitRequest(String userId, Integer timestamp) {
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }
}
