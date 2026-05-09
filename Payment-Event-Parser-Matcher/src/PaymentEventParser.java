import java.util.*;

public class PaymentEventParser {
    public static final String EVENT_ID_KEY = "event_id";
    public static final String MERCHANT_ID_KEY = "merchant";
    public static final String CURRENCY_ID_KEY = "currency";
    public static final String AMOUNT_ID_KEY = "amount";

    private final Set<String> keySet;
    private final Set<String> validEventIds;

    public PaymentEventParser(Set<String> validEventIds) {
        this.validEventIds = validEventIds;
        this.keySet = new HashSet<>();
        keySet.add(EVENT_ID_KEY);
        keySet.add(CURRENCY_ID_KEY);
        keySet.add(AMOUNT_ID_KEY);
        keySet.add(MERCHANT_ID_KEY);
    }

    List<EventDto> parseValidEvents(
            List<String> rawEvents
    ) {
        //Null check
        if (rawEvents == null) return List.of();

        List<EventDto> result = new ArrayList<>();

        // iterate over logs
        for (String event : rawEvents) {
            Optional<EventDto> eventOptional = parseRawEvent(event);
            if (eventOptional.isEmpty()) continue;

            if (!isValidEvent(eventOptional.get())) {
                continue;
            }
            // valid event
            result.add(eventOptional.get());
        }

        return result;
    }

    private boolean isValidEvent(EventDto eventDto) {
        String eventId = eventDto.getEventID();
        // validate eventID
        if (eventId == null || !validEventIds.contains(eventId) || eventId.isBlank()) return false;

        // validate amount
        int amount = eventDto.getAmount();
        if (amount < 0) return false;

        //validate currency
        String currency = eventDto.getCurrency();
        if (currency == null || currency.isBlank()) return false;

        return true;

    }

    private Optional<EventDto> parseRawEvent(String event) {

        if (event == null || event.isBlank()) return Optional.empty(); // -> empty Event and null Event UT

        String[] keyValuePairsList = event.split(";");

        String eventID = "";
        String currency = "";
        String merchant = "";
        int amount = 0;

//      event_id=evt_1;merchant=amazon;amount=100;currency=USD"
        Set<String> visitedKeys = new HashSet<>();
        for (String keyValuePair : keyValuePairsList) {
            // 0. pruning for optimisation
            if (!keyValuePair.contains("=")) return Optional.empty();

            // 1. check valid K=V pattern is present in this split or not -> Ut for same
            String[] keyValuePairSplit = keyValuePair.split("=", 2);
            if (keyValuePairSplit.length != 2) return Optional.empty();

            String key = keyValuePairSplit[0];
            String value = keyValuePairSplit[1];

//          2. check whether this key is redundant And if not, then check whether it's a duplicate Key -> 2 UT's
            if (!keySet.contains(key) || visitedKeys.contains(key)) return Optional.empty();

            visitedKeys.add(key);
            switch (key) {
                case EVENT_ID_KEY -> eventID = value;
                case AMOUNT_ID_KEY -> {
                    try {
                        amount = Integer.parseInt(value); // ut
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                }
                case CURRENCY_ID_KEY -> currency = value;
                case MERCHANT_ID_KEY -> merchant = value;
            }
        }
        // 3. check whether this event contains all required fields or not
        if (!visitedKeys.contains(EVENT_ID_KEY) || !visitedKeys.contains(CURRENCY_ID_KEY) || !visitedKeys.contains(AMOUNT_ID_KEY)) {
            return Optional.empty();
        }
        return Optional.of(new EventDto(eventID, merchant, currency, amount));
    }
}
