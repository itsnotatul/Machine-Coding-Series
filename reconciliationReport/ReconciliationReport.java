import java.util.List;
import java.util.Map;

public class ReconciliationReport {
    private int validTransactionCounter;
    private int inValidTransactionCounter;
    private List<String> duplicateTransactionIdList; /** need to store only TxnId's**/
    private Map<String, Integer> currencyToNetBalanceMap;
    private List<String> suspiciousTransactionIdList;

    public int getValidTransactionCounter() {
        return validTransactionCounter;
    }

    public void setValidTransactionCounter(int validTransactionCounter) {
        this.validTransactionCounter = validTransactionCounter;
    }

    public int getInValidTransactionCounter() {
        return inValidTransactionCounter;
    }

    public void setInValidTransactionCounter(int inValidTransactionCounter) {
        this.inValidTransactionCounter = inValidTransactionCounter;
    }

    public List<String> getDuplicateTransactionIdList() {
        return duplicateTransactionIdList;
    }

    public void setDuplicateTransactionIdList(List<String> duplicateTransactionIdList) {
        this.duplicateTransactionIdList = duplicateTransactionIdList;
    }

    public Map<String, Integer> getCurrencyToNetBalanceMap() {
        return currencyToNetBalanceMap;
    }

    public void setCurrencyToNetBalanceMap(Map<String, Integer> currencyToNetBalanceMap) {
        this.currencyToNetBalanceMap = currencyToNetBalanceMap;
    }

    public List<String> getSuspiciousTransactionIdList() {
        return suspiciousTransactionIdList;
    }

    public void setSuspiciousTransactionIdList(List<String> suspiciousTransactionIdList) {
        this.suspiciousTransactionIdList = suspiciousTransactionIdList;
    }

    @Override
    public String toString() {
        return "ReconciliationReport{" +
                "validTransactionCounter=" + validTransactionCounter +
                ", inValidTransactionCounter=" + inValidTransactionCounter +
                ", duplicateTransactionIdList=" + duplicateTransactionIdList +
                ", currencyToNetBalanceMap=" + currencyToNetBalanceMap +
                ", suspiciousTransactionIdList=" + suspiciousTransactionIdList +
                '}';
    }
}
