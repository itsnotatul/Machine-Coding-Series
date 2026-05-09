import java.util.*;

public class RateLimiter {
    private final Map<String, Deque<RateLimitRequest>> userIdToRequestMap;
    private static final int RATE_LIMIT_WINDOW_SIZE = 60;
    private static final int ALLOWED_REQUESTS_PER_WINDOW = 5;

    RateLimiter() {
        userIdToRequestMap = new HashMap<>();
    }

    public boolean allow(String userId, int timestamp) {
        RateLimitRequest request = new RateLimitRequest(userId, timestamp);

        boolean result = isValidRequest(request);
        if(result) addRequestToUserIdToRequestMap(request); // add request only after accepting the request

        return result;
    }

    private boolean isValidRequest(RateLimitRequest request) {
        /** Empty history/ first request should pass through **/
        if (!userIdToRequestMap.containsKey(request.getUserId())) {
            return true;
        }
        Deque<RateLimitRequest> currentWindow = userIdToRequestMap.get(request.getUserId());

        // evict out of window elements
        int endTimestamp = request.getTimestamp();
        int startTimestamp = endTimestamp - RATE_LIMIT_WINDOW_SIZE + 1; // boundary check

        while( !currentWindow.isEmpty() && currentWindow.peekFirst().getTimestamp() < startTimestamp){
            currentWindow.pollFirst();
        }

        if (currentWindow.size() >= ALLOWED_REQUESTS_PER_WINDOW)
            return false; // if num. of accepted requests in curr window >5 -> reject this request

        return true;
    }

//    private int countAcceptedRequestsInCurrWindow(RateLimitRequest request) {
//        String userId = request.getUserId();
//        int endTimestamp = request.getTimestamp();
//        int startTimestamp = endTimestamp - RATE_LIMIT_WINDOW_SIZE + 1; // boundary check
//
//        /** Empty history/ first request should pass through
//         * that's why we used getOrDefault()
//         * **/
//
//        List<RateLimitRequest> currRequestList = userIdToRequestMap.get(userId);
//        if(currRequestList.isEmpty()) return 0;
//        //find Index of lower bound for startTimestamp
//        int lowerBoundIdx = getLowerBound(currRequestList, startTimestamp);
//
//        int counter = 0;
//        for (int i = lowerBoundIdx; i < currRequestList.size(); i++) {
//            if (currRequestList.get(i).getTimestamp() > endTimestamp) break;
//            counter++;
//        }
//
//        return counter;

//    }

    private int getLowerBound(List<RateLimitRequest> currRequestList, int startTimestamp) {
        int low = 0;
        int high = currRequestList.size();

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (currRequestList.get(mid).getTimestamp() < startTimestamp) {
                low = mid + 1;
            } else high = mid;
        }
        return low;
    }

    private void addRequestToUserIdToRequestMap(RateLimitRequest request) {
        String userId = request.getUserId();
        if (!userIdToRequestMap.containsKey(userId)) {
            userIdToRequestMap.put(userId, new ArrayDeque<>(List.of(request)));
        } else {
            userIdToRequestMap.get(userId).offerLast(request); // Deque is a reference
        }
    }
}
