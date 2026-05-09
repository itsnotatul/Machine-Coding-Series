import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentEventParserTest {

   public static void testEmptyEventsList(){
       Set<String> validEventIdSet = new HashSet<>();
       validEventIdSet.add("1");
       validEventIdSet.add("2");

       List<String> rawEvents = new ArrayList<>();

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
       List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testEmptyEventsList : fail");
        }
       System.out.println("testEmptyEventsList : pass");
   }

    public static void testNullEventsList(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(null);

        if(!result.isEmpty()){
            throw new RuntimeException("testNullEventsList : fail");
        }
        System.out.println("testNullEventsList : pass");
    }

    public static void testEmptyEventInEventsList(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("1");
        validEventIdSet.add("2");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testEmptyEventInEventsList : fail");
        }
        System.out.println("testEmptyEventInEventsList : pass");
    }

    public static void testNullEventInEventsList(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add(null);

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testNullEventInEventsList : fail");
        }
        System.out.println("testNullEventInEventsList : pass");
    }

    public static void testInvalidKeyValuePairInEvent(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("currency=USD===;merchant=amazon;event_idevt_1;amount=100"); // invalid k-v pair for eventId
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testInvalidKeyValuePairInEvent : fail");
        }
        System.out.println("testInvalidKeyValuePairInEvent : pass");
    }

    public static void testDuplicateKeysFails(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("currency=USD;merchant=amazon;event_id=evt_1;amount=100;amount=200");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testDuplicateKeysFails : fail");
        }
        System.out.println("testDuplicateKeysFails : pass");
    }

    public static void testRedundantKeysInEventShouldFail(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("currency=USD;merchant=amazon;event_id=evt_1;amount=100;random=200");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testRedundantKeysInEventShouldFail : fail");
        }
        System.out.println("testRedundantKeysInEventShouldFail : pass");
    }

    public static void testNonIntegerAmountInEvent(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("event_id=evt_1;merchant=amazon;amount=abc;currency=USD");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testNonIntAmount : fail");
        }
        System.out.println("testNonIntAmount : pass");
    }

    public static void testAllRequiredKeysShouldPresentInEvent(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("merchant=amazon;event_id=evt_1;amount=100");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testAllRequiredKeysShouldPresentInEvent : fail");
        }
        System.out.println("testAllRequiredKeysShouldPresentInEvent : pass");
    }

    public static void testEmptyEventIdInEvent(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("event_id=;merchant=amazon;amount=100;currency=USD");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testEmptyEventIdInEvent : fail");
        }
        System.out.println("testEmptyEventIdInEvent : pass");
    }

    public static void testNegativeAmount(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("event_id=evt_1;merchant=amazon;amount=-100;currency=USD");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testNegativeAmount : fail");
        }
        System.out.println("testNegativeAmount : pass");
    }

    public static void testEmptyCurrency(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("event_id=evt_1;merchant=amazon;amount=100;currency=");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(!result.isEmpty()){
            throw new RuntimeException("testEmptyCurrency : fail");
        }
        System.out.println("testEmptyCurrency : pass");
    }

    public static void testValidEvents(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("event_id=evt_1;merchant=amazon;amount=100;currency=USD");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(result.size() != 1 || !result.get(0).getEventID().equals("evt_1")){
            throw new RuntimeException("testValidEvents : fail");
        }
        System.out.println("testValidEvents : pass");
    }

    /**
     * Below are edge cases handling related test cases
     */

    public static void testOutOfOrderKVPairs(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("currency=USD;merchant=amazon;event_id=evt_1;amount=100");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(result.size() != 1 || !result.get(0).getEventID().equals("evt_1")){
            throw new RuntimeException("testOutOfOrderKVPairs : fail");
        }
        System.out.println("testOutOfOrderKVPairs : pass");
    }

    public static void testDuplicateEventsTreatedDistinct(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("currency=USD;merchant=amazon;event_id=evt_1;amount=100");
        rawEvents.add("currency=USD;merchant=amazon;event_id=evt_1;amount=100");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(result.size() != 2 || !result.get(0).getEventID().equals("evt_1") || !result.get(1).getEventID().equals("evt_1")){
            throw new RuntimeException("testDuplicateEventsTreatedDistinct : fail");
        }
        System.out.println("testDuplicateEventsTreatedDistinct : pass");
    }

    public static void testValueWithMultipleDelimiters(){
        Set<String> validEventIdSet = new HashSet<>();
        validEventIdSet.add("evt_1");
        validEventIdSet.add("evt_3");

        List<String> rawEvents = new ArrayList<>();
        rawEvents.add("currency=USD===;merchant=amazon;event_id=evt_1;amount=100");
        rawEvents.add("event_id=evt_2;merchant=uber;amount=-50;currency=USD");
        rawEvents.add("event_id=evt_3;merchant=netflix;amount=200");

        PaymentEventParser paymentEventParser = new PaymentEventParser(validEventIdSet);
        List<EventDto> result =  paymentEventParser.parseValidEvents(rawEvents);

        if(result.size() != 1 || !result.get(0).getEventID().equals("evt_1") || !result.get(0).getCurrency().equals("USD===")){
            throw new RuntimeException("testValueWithMultipleDelimiters : fail");
        }
        System.out.println("testValueWithMultipleDelimiters : pass");
    }

}
