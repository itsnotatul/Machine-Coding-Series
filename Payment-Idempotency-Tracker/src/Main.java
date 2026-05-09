public class Main {
    public static void main(String[] args) {
        PaymentIdempotencyTracker paymentIdempotencyTracker = new PaymentIdempotencyTracker();

        try {
            System.out.println("Result: " + paymentIdempotencyTracker.process("k1", "charge_100_usd"));
            System.out.println("Result: " + paymentIdempotencyTracker.process("k1", "charge_100_usd"));
            System.out.println("Result: " + paymentIdempotencyTracker.process("k1", "charge_200_usd"));
            System.out.println("Result: " + paymentIdempotencyTracker.process("k2", "refund_50"));
        } catch (RuntimeException e) {
            System.out.println(e);
        }

        System.out.println("========= TEST COVERAGE BELOW =========");
        try {
            PaymentIdempotencyTrackerTest.testEmptyRecord();
            PaymentIdempotencyTrackerTest.testNullRecord();
            PaymentIdempotencyTrackerTest.testFristRecord();
            PaymentIdempotencyTrackerTest.testDuplicateRecord();
            PaymentIdempotencyTrackerTest.testConflictRecord();
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
