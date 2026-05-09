public class Main {
    public static void main(String[] args) {
        System.out.println("======= TEST COVERAGE =====");

        try {
            System.out.print("test 1:" );
            PaymentEventParserTest.testEmptyEventsList();
            System.out.print("test 2:" );
            PaymentEventParserTest.testNullEventsList();
            System.out.print("test 3:" );
            PaymentEventParserTest.testEmptyEventInEventsList();
            System.out.print("test 4:" );
            PaymentEventParserTest.testNullEventInEventsList();
            System.out.print("test 5:" );
            PaymentEventParserTest.testInvalidKeyValuePairInEvent();
            System.out.print("test 6:" );
            PaymentEventParserTest.testDuplicateKeysFails();
            System.out.print("test 7:" );
            PaymentEventParserTest.testRedundantKeysInEventShouldFail();
            System.out.print("test 8:" );
            PaymentEventParserTest.testNonIntegerAmountInEvent();
            System.out.print("test 9:" );
            PaymentEventParserTest.testAllRequiredKeysShouldPresentInEvent();
            System.out.print("test 10:" );
            PaymentEventParserTest.testEmptyEventIdInEvent();
            System.out.print("test 11:" );
            PaymentEventParserTest.testNegativeAmount();
            System.out.print("test 12:" );
            PaymentEventParserTest.testEmptyCurrency();
            System.out.print("test 13:" );
            PaymentEventParserTest.testValidEvents();
            System.out.print("test 14:" );
            PaymentEventParserTest.testOutOfOrderKVPairs();
            System.out.print("test 15:" );
            PaymentEventParserTest.testDuplicateEventsTreatedDistinct();
            System.out.print("test 16:" );
            PaymentEventParserTest.testValueWithMultipleDelimiters();
        } catch (Exception e) {
            System.out.println("=========== Test coverage failed ======== with exception: " + e);
        }
    }
}