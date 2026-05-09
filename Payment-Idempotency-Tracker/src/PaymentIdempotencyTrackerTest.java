public final class PaymentIdempotencyTrackerTest {

    public static void testEmptyRecord(){
        PaymentIdempotencyTracker paymentIdempotencyTracker = new PaymentIdempotencyTracker();
        ResultDto resultDto = paymentIdempotencyTracker.process("k1", "");
        if(!resultDto.getStatus().equals(ResultDto.ResultStatus.INVALID)){
            throw  new RuntimeException("testEmptyRecord: fail");
        }
        System.out.println("testEmptyRecord: pass");
    }
    public static void testNullRecord(){
        PaymentIdempotencyTracker paymentIdempotencyTracker = new PaymentIdempotencyTracker();
        ResultDto resultDto = paymentIdempotencyTracker.process("k1", null);
        if(!resultDto.getStatus().equals(ResultDto.ResultStatus.INVALID)){
            throw  new RuntimeException("testNullRecord: fail");
        }
        System.out.println("testNullRecord: pass");
    }
    public static void testFristRecord(){
        PaymentIdempotencyTracker paymentIdempotencyTracker = new PaymentIdempotencyTracker();
        ResultDto resultDto = paymentIdempotencyTracker.process("k1", "charge_100_usd");
        if(!resultDto.getStatus().equals(ResultDto.ResultStatus.SUCCESS)){
            throw  new RuntimeException("testFristRecord: fail with status: "  + resultDto.getStatus());
        }
        System.out.println("testFristRecord: pass");

    }
    public static void testDuplicateRecord(){
        PaymentIdempotencyTracker paymentIdempotencyTracker = new PaymentIdempotencyTracker();
        ResultDto resultDto1 = paymentIdempotencyTracker.process("k1", "charge_100_usd");
        ResultDto resultDto2 = paymentIdempotencyTracker.process("k1", "charge_100_usd");
        if(!resultDto2.getStatus().equals(ResultDto.ResultStatus.SUCCESS)){
            throw  new RuntimeException("testDuplicateRecord: fail with status: "  + resultDto2.getStatus());
        }
        System.out.println("testDuplicateRecord: pass");
    }
    public static void testConflictRecord(){
        PaymentIdempotencyTracker paymentIdempotencyTracker = new PaymentIdempotencyTracker();
        ResultDto resultDto1 = paymentIdempotencyTracker.process("k1", "charge_100_usd");
        ResultDto resultDto2 = paymentIdempotencyTracker.process("k1", "charge_101_usd");
        if(!resultDto2.getStatus().equals(ResultDto.ResultStatus.CONFLICT)){
            throw  new RuntimeException("testConflictRecord: fail with status: " + resultDto2.getStatus());
        }
        System.out.println("testConflictRecord: pass");

    }
}
