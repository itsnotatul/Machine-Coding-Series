public class Main {
    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter();
        System.out.println("result: " + rateLimiter.allow("alice", 1001));
        System.out.println("result: " + rateLimiter.allow("alice", 1002));
        System.out.println("result: " + rateLimiter.allow("alice", 1003));
        System.out.println("result: " + rateLimiter.allow("alice", 1004));
        System.out.println("result: " + rateLimiter.allow("alice", 1060));
        System.out.println("result: " + rateLimiter.allow("alice", 1060));
        System.out.println("result: " + rateLimiter.allow("alice", 1070));

        System.out.println("============ TEST COVERAGE BELOW ===========");
        try {
            RateLimiterTest.testDuplicateRequests();
            RateLimiterTest.testBasicRequests();
            RateLimiterTest.testBoundaryRequests();
            RateLimiterTest.testEmptyHistory();
            RateLimiterTest.testDifferentUsersRequestsDoNotInterfere();
            RateLimiterTest.testEvictionPolicy();
        } catch (RuntimeException e) {
            System.out.println("TEST COVERAGE FAILED: Please recheck your logic");
        }

    }
}