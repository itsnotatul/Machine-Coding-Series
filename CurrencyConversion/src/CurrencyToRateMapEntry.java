public class CurrencyToRateMapEntry {
    private String currency;
    private Double exchangeRate;

    public CurrencyToRateMapEntry(String currency, Double exchangeRate) {
        this.currency = currency;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
