package khozhaev.currencycalculator;

import khozhaev.currencycalculator.exhanger.CurrencyExchangerFactory;

import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String rublesToDollarsRateStr = RateConfiguration.getProperty("rublesToDollars");
        if (rublesToDollarsRateStr == null) {
            throw new RuntimeException("Property \"rublesToDollars\" is undefined!");
        }
        double rublesToDollarsRate = Double.parseDouble(rublesToDollarsRateStr);
        CurrencyExchangerFactory currencyExchangerFactory = new CurrencyExchangerFactory(
                Collections.singletonMap("rublesToDollars", rublesToDollarsRate)
        );

        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();

        Calculator calculator = new Calculator(currencyExchangerFactory);
        String result = calculator.calculate(expression);
        System.out.println(result);
    }
}
