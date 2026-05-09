import java.util.*;

public class CurrencyExchange {
    Map<String, List<CurrencyToRateMapEntry>> currencyToRateMap;

    public CurrencyExchange(List<Rate> rates) {

        currencyToRateMap = new HashMap<>();
        /** Map both fwd and bwd rate list **/
        for (Rate rate : rates) {
            String fwdKey = rate.getFromCurrency();
            String bwdKey = rate.getToCurrency();
            addEdgeToCurrencyToRateMap(fwdKey, rate.getToCurrency(), rate.getExchangeRate());
            addEdgeToCurrencyToRateMap(bwdKey, rate.getFromCurrency(), 1.0 / rate.getExchangeRate());
        }
    }

    private void addEdgeToCurrencyToRateMap(String key, String currency, Double exchangeRate) {
        if (!currencyToRateMap.containsKey(key)) {
            currencyToRateMap.put(key, new ArrayList<>(List.of(new CurrencyToRateMapEntry(currency, exchangeRate))));
        } else {
            var currList = currencyToRateMap.get(key);
            currList.add(new CurrencyToRateMapEntry(currency, exchangeRate));
            currencyToRateMap.put(key, currList);
        }
    }

    public Optional<Double> convert(Double amount, String fromCurrency, String toCurrency) {
        if(fromCurrency.equals(toCurrency)){
            System.out.println("Same from and To conversion request logged: ");
            return Optional.ofNullable(amount);
        }
        if(!currencyToRateMap.containsKey(fromCurrency) || !currencyToRateMap.containsKey(toCurrency)) {
            System.out.println("Invalid Request Raised, we didn't find the currency exchange rates in the given rateList");
            return Optional.empty();
        }

        Optional<Double> exchangeRate = null;
        try {
            exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        } catch (IllegalArgumentException e) {
            System.out.println("No conversion path found, returning null");
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Some Internal Error Occured, returning null. Error Message: "  + e.getMessage());
            return Optional.empty();
        }
        return Optional.of(calculateFinalAmount(amount, exchangeRate.get()));
    }

    private Optional<Double> getExchangeRate(String fromCurrency, String toCurrency) {

        // find toCurrency in this list
        var exchangeRateList = currencyToRateMap.get(fromCurrency);
        for (var exchangeRate : exchangeRateList) {
            if (exchangeRate.getCurrency().equals(toCurrency)) {
                System.out.println("Found the exchange rate for: " + fromCurrency + " to: " + toCurrency + " :" + exchangeRate.getExchangeRate());
                return Optional.ofNullable(exchangeRate.getExchangeRate());
            }
        }

        System.out.println("No direct edge found b/w: " + fromCurrency + " & " + toCurrency);
        return dfs(fromCurrency, toCurrency, new HashSet<>(), 1.0);
    }

    private Optional<Double> dfs(String currNode, String targetNode, Set<String> visited, double runningRate) {

        if (currNode.equals(targetNode)) {
            return Optional.of(runningRate);
        }

        if (visited.contains(currNode)) {
            return Optional.empty();
        }

        visited.add(currNode);

        var neighbors = currencyToRateMap.get(currNode);

        for (var neighbor : neighbors) {
            Optional<Double> result = dfs(
                    neighbor.getCurrency(),
                    targetNode,
                    visited,
                    runningRate * neighbor.getExchangeRate()
            );

            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.empty();
    }

    private Double calculateFinalAmount(Double amount, Double exchangeRate) {
        return amount * exchangeRate;
    }
}
