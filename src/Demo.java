import java.util.*;

public class Demo {
    static Map<String, Integer> currencyToNetBalanceMap;
    static Map<String, Integer> transactionIdToNetBalanceMap;
//    static Map<String, TransactionLogDto> idempotentKeyToTransactionLogMap; // this will contain unique transaction logs
    static Set<String> transactionSet;
    static Integer invalidTransactionCounter;
    static Set<String> suspiciousTransactionIdSet;
    static Set<String> duplicateTransactionIdSet;

    static ReconciliationReport reconcile(List<String> transactions) {
        currencyToNetBalanceMap = new HashMap<>();
        transactionIdToNetBalanceMap = new HashMap<>();
        transactionSet = new HashSet<>();
        suspiciousTransactionIdSet = new HashSet<>();
        duplicateTransactionIdSet = new HashSet<>();

        ReconciliationReport reconciliationReport = new ReconciliationReport();

        invalidTransactionCounter = 0;

        for (String transaction : transactions) {
            //basic validation check
            if (!isValidTransaction(transaction)) {
                invalidTransactionCounter++;
                continue;
            }

            String[] transactionParts = transactionParts(transaction);
            //deduplication check
            if (isDuplicateRecord(transaction)) {
                duplicateTransactionIdSet.add(transactionParts[1]);
                invalidTransactionCounter++;
                continue;
            }
            // fill currency to net balance map
            fillCurrencyToNetBalanceMap(transaction);
            fillTransactionIdToNetBalanceMap(transaction);
        }

        for(Map.Entry<String, Integer> entry: transactionIdToNetBalanceMap.entrySet()){
            if(entry.getValue() < 0){
                suspiciousTransactionIdSet.add(entry.getKey());
            }
        }

        reconciliationReport.setInValidTransactionCounter(invalidTransactionCounter);
        reconciliationReport.setValidTransactionCounter((transactions.size() - invalidTransactionCounter));
        reconciliationReport.setDuplicateTransactionIdList(duplicateTransactionIdSet.stream().toList());
        reconciliationReport.setCurrencyToNetBalanceMap(currencyToNetBalanceMap);
        reconciliationReport.setSuspiciousTransactionIdList(suspiciousTransactionIdSet.stream().toList());

        return reconciliationReport;
    }

    private static void fillTransactionIdToNetBalanceMap(String transaction) {
        String[] transactionParts = transactionParts(transaction);

        TransactionType transactionType =  TransactionType.valueOf(transactionParts[0]);
        String transactionId = transactionParts[1];
        Integer amount = Integer.parseInt(transactionParts[2]);
        Integer effectiveAmount = amount;

        effectiveAmount =  (transactionType.equals(TransactionType.PAYMENT))? effectiveAmount: (effectiveAmount*-1);
        if(transactionIdToNetBalanceMap.containsKey(transactionId)){
            transactionIdToNetBalanceMap.put(transactionId, transactionIdToNetBalanceMap.get(transactionId) + effectiveAmount);
        } else {
            transactionIdToNetBalanceMap.put(transactionId, effectiveAmount);
        }
    }

    private static void fillCurrencyToNetBalanceMap(String transaction) {
        String[] transactionParts = transactionParts(transaction);

        TransactionType transactionType =  TransactionType.valueOf(transactionParts[0]);
        Integer amount = Integer.parseInt(transactionParts[2]);
        String currency = transactionParts[3];
        Integer effectiveAmount = amount;

        effectiveAmount =  (transactionType.equals(TransactionType.PAYMENT))? effectiveAmount: (effectiveAmount*-1);
        if(currencyToNetBalanceMap.containsKey(currency)){
            currencyToNetBalanceMap.put(currency, currencyToNetBalanceMap.get(currency) + effectiveAmount);
        } else {
            currencyToNetBalanceMap.put(currency, effectiveAmount);
        }
    }

    private static String[] transactionParts(String transaction) {
        return transaction.split("\\|");
    }

    private static boolean isDuplicateRecord(String transaction) {
        if (transactionSet.contains(transaction)) {
            return true;
        }
        transactionSet.add(transaction);
        return false;
    }

    private static boolean isValidTransaction(String transaction) {
        //HELLO|txn1|100|USD
        if(transaction == null) return false;
        String[] transactionParts = transactionParts(transaction);
        if (transactionParts.length != 4) return false;

        String transactionType = transactionParts[0];
        String transactionId = transactionParts[1];
        Integer amount = Integer.parseInt(transactionParts[2]);
        String currency = transactionParts[3];

        if (amount < 0) return false;

        try {
            TransactionType.valueOf(transactionType);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Transaction Type");
            return false;
        }

        if (transactionId.isBlank() || currency.isBlank()) return false;

        return true;
    }

    public static void main(String[] args) {
        ReconciliationReport reconciliationReport = reconcile(Arrays.asList("PAYMENT|txn1|100|USD", "PAYMENT|txn2|200|USD", "REFUND|txn1|50|USD", "CHARGEBACK|txn2|200|USD", "PAYMENT|txn1|100|USD", "INVALID_RECORD", "PAYMENT|txn3|-100|USD", "REFUND|txn1|1000|USD"));
        System.out.println(reconciliationReport);
    }
}
