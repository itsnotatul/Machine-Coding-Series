import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public final class CurrencyExchangeTest {

    private static CurrencyExchange currencyExchange;

    public static void setup() {
        List<Rate> rateList = List.of(new Rate("USD", "EUR", 0.9));
        currencyExchange = new CurrencyExchange(rateList);
    }

    static void testFwdCurrencyConverion_HappyFlow() {
        // USD -> EUR
        Optional<Double> finalRate = currencyExchange.convert(100.0, "USD", "EUR");
        if (finalRate.isEmpty()) {
            System.out.println("Basic Fwd Conversion Failed with null");
            throw new RuntimeException();
        }
        if (finalRate.isPresent() && finalRate.get() != 90.0) {
            System.out.println("Basic FWD Conversion Failed with :" + finalRate.get() + " and expected was : 90.0");
            throw new RuntimeException();
        }
        System.out.println("FWD Conversion Basic Test Passed");
    }

    static void testBwdCurrencyConverion_HappyFlow() {
        // USD -> EUR
        Optional<Double> finalRate = currencyExchange.convert(100.0, "EUR", "USD");
        if (finalRate.isEmpty()) {
            System.out.println("Basic BWD Conversion Failed with null");
            throw new RuntimeException();
        }
        Double rounded = new BigDecimal(finalRate.get()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        if (finalRate.isPresent() && rounded != 111.11) {
            System.out.println("Basic BWD Conversion Failed with :" + finalRate.get() + " and expected was : 111.1111");
            throw new RuntimeException();
        }
        System.out.println("BWD Conversion Basic Test Passed");
    }

    static void testCurrencyConverion_FailureFlow() {
        // USD -> EUR
        Optional<Double> finalRate = currencyExchange.convert(100.0, "USD", "INR");
        if (!finalRate.isEmpty()) {
            System.out.println("testCurrencyConverion_FailureFlow::Failed. Expected: null " + "Actual: " + finalRate.get());
            throw new RuntimeException();
        }

        System.out.println("testCurrencyConverion_FailureFlow::Failed. Expected: null " + "Actual: " + finalRate.get());
    }

}
