public final class RateLimiterTest {

    public static void testDuplicateRequests() {
        RateLimiter rateLimiter = new RateLimiter();
        //setup
      boolean reqResult1 =  rateLimiter.allow("alice", 1000);
      boolean reqResult2 =  rateLimiter.allow("alice", 1000);
      boolean reqResult3 =  rateLimiter.allow("alice", 1000);
      boolean reqResult4 =  rateLimiter.allow("alice", 1000);
      boolean reqResult5 =  rateLimiter.allow("alice", 1000);
      boolean reqResult6 =  rateLimiter.allow("alice", 1000);
        //assert
        if(!reqResult6 && reqResult1 && reqResult2 && reqResult3 && reqResult4 && reqResult5){
            System.out.println("testDuplicateRequests:: pass" );
        }
        else {
            String error = ("testDuplicateRequests:: fail with this status: " + !reqResult6 + reqResult1 + reqResult2 + reqResult3 + reqResult4 + reqResult5);
            throw new RuntimeException(error);

        }
    }

    public static void testBasicRequests() {
        RateLimiter rateLimiter = new RateLimiter();
        //setup
        boolean reqResult1 =  rateLimiter.allow("alice", 1000);
        boolean reqResult2 =  rateLimiter.allow("alice", 1010);
        boolean reqResult3 =  rateLimiter.allow("alice", 1020);
        boolean reqResult4 =  rateLimiter.allow("alice", 1030);
        boolean reqResult5 =  rateLimiter.allow("alice", 1040);
        boolean reqResult6 =  rateLimiter.allow("alice", 1050);
        //assert
        if(!reqResult6 && reqResult1 && reqResult2 && reqResult3 && reqResult4 && reqResult5){
            System.out.println("testBasicRequests:: pass" );
        }
        else {
            String error = ("testBasicRequests:: fail with this status: " + !reqResult6 + reqResult1 + reqResult2 + reqResult3 + reqResult4 + reqResult5);
            throw new RuntimeException(error);
        }
    }

    public static void testBoundaryRequests() {
        RateLimiter rateLimiter = new RateLimiter();
        //setup
        boolean reqResult1 =  rateLimiter.allow("alice", 1001);
        boolean reqResult2 =  rateLimiter.allow("alice", 1012);
        boolean reqResult3 =  rateLimiter.allow("alice", 1020);
        boolean reqResult4 =  rateLimiter.allow("alice", 1030);
        boolean reqResult5 =  rateLimiter.allow("alice", 1060);
        boolean reqResult6 =  rateLimiter.allow("alice", 1060);
        //assert
        if(!reqResult6 && reqResult1 && reqResult2 && reqResult3 && reqResult4 && reqResult5){
            System.out.println("testBoundaryRequests:: pass" );
        }
        else {
            String error = ("testBoundaryRequests:: fail with this status: " + !reqResult6 + reqResult1 + reqResult2 + reqResult3 + reqResult4 + reqResult5);
            throw new RuntimeException(error);
        }
    }

    public static void testEmptyHistory() {
        RateLimiter rateLimiter = new RateLimiter();
        //setup
        boolean reqResult1 =  rateLimiter.allow("alice", 1000);
        //assert
        if(reqResult1){
            System.out.println("testEmptyHistory:: pass" );
        }
        else {
            String error = ("testEmptyHistory:: fail with this status: " +  reqResult1 );
            throw new RuntimeException(error);
        }
    }

    public static void testDifferentUsersRequestsDoNotInterfere() {
        RateLimiter rateLimiter = new RateLimiter();
        //setup
        boolean reqResult1 =  rateLimiter.allow("alice", 1001);
        boolean reqResult2 =  rateLimiter.allow("alice", 1012);
        boolean reqResult3 =  rateLimiter.allow("alice", 1020);
        boolean reqResult4 =  rateLimiter.allow("alice", 1030);
        boolean reqResult5 =  rateLimiter.allow("alice", 1060);
        boolean reqResult6 =  rateLimiter.allow("alice", 1060);
        boolean tomRequest =  rateLimiter.allow("tom", 1060);
        //assert
        if(!reqResult6 && tomRequest){
            System.out.println("testDifferentUsersRequestsDoNotInterfere:: pass" );
        }
        else {
            String error = ("testDifferentUsersRequestsDoNotInterfere:: fail with this status: " +  reqResult1 );
            throw new RuntimeException(error);
        }
    }

    public static void testEvictionPolicy() {
        RateLimiter rateLimiter = new RateLimiter();
        //setup
        boolean reqResult1 =  rateLimiter.allow("alice", 1001);
        boolean reqResult2 =  rateLimiter.allow("alice", 1012);
        boolean reqResult3 =  rateLimiter.allow("alice", 1020);
        boolean reqResult4 =  rateLimiter.allow("alice", 1030);
        boolean reqResult5 =  rateLimiter.allow("alice", 1060);
        boolean reqResult6 =  rateLimiter.allow("alice", 1061); // should pass too
        //assert
        if(reqResult6){
            System.out.println("testEvictionPolicy:: pass" );
        }
        else {
            String error = ("testEvictionPolicy:: fail with this status: " +  reqResult6 );
            throw new RuntimeException(error);
        }
    }

}
