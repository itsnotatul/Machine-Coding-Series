import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<Rate> rateList = List.of(new Rate("USD", "EUR", 0.9),
                new Rate("USD", "JPY", 150.0),
                new Rate("EUR", "GBP", 0.85));
        CurrencyExchange currencyExchange = new CurrencyExchange(rateList);

        Optional<Double> exchangeRateOptional = currencyExchange.convert(100.0, "USD", "GBP");
        if(!exchangeRateOptional.isPresent()){
            System.out.println("No conversion possible based on given rateList");
        } else
        System.out.println("Converted amount: " + exchangeRateOptional.get());

        // tests
        System.out.println("==================== Test Cases Results Below =============");
        try {
            CurrencyExchangeTest.setup();
            CurrencyExchangeTest.testFwdCurrencyConverion_HappyFlow();
            CurrencyExchangeTest.testBwdCurrencyConverion_HappyFlow();
            CurrencyExchangeTest.testCurrencyConverion_FailureFlow();
        } catch (Exception e) {
            System.out.println("test cases failed");
        }
    }
}