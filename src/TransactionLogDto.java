public class TransactionLogDto {
    private TransactionType transactionType;
    private String transactionId;
    private int amount;
    private String currency;

    public TransactionLogDto(TransactionType transactionType, String transactionId, int amount, String currency) {
        this.transactionType = transactionType;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
