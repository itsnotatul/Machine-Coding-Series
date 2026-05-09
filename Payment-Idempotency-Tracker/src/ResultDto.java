public class ResultDto {
    private final ResultStatus status;
    private final String transactionId;

    public ResultDto(ResultStatus status, String transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public enum ResultStatus {
        SUCCESS,
        REJECT,
        CONFLICT,
        INVALID;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "status='" + status + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
