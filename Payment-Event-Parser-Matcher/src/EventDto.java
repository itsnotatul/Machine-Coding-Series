import java.util.Objects;

public class EventDto {
    private final String eventID;
    private final String merchant;
    private final String currency;
    private final int amount;

    public EventDto(String eventID, String merchant, String currency, int amount) {
        this.eventID = eventID;
        this.merchant = merchant;
        this.currency = currency;
        this.amount = amount;
    }

    public String getEventID() {
        return eventID;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, merchant, currency, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;

        if(!(obj instanceof EventDto objInstance)) return false;

        return Objects.equals(eventID, objInstance.eventID) && Objects.equals(merchant, objInstance.merchant)
                && Objects.equals(currency, objInstance.currency) && amount == objInstance.amount; // primitive data type
    }
}
