package khozhaev.currencycalculator.exhanger;

import java.util.*;
import java.util.stream.Collectors;

public class CurrencyExchangerFactory {

    private final List<CurrencyExchanger> currencyExchangers;

    public CurrencyExchangerFactory(Map<String, Double> currencyRateMap) {
        currencyExchangers = new ArrayList<>();
        if (currencyRateMap.containsKey("rublesToDollars")) {
            Double rate = currencyRateMap.get("rublesToDollars");
            currencyExchangers.add(new DollarsToRublesExchanger(rate));
            currencyExchangers.add(new RublesToDollarsExchanger(1/rate));
        } else if (currencyRateMap.containsKey("dollarsToRubles")) {
            Double rate = currencyRateMap.get("dollarsToRubles");
            currencyExchangers.add(new DollarsToRublesExchanger(1/rate));
            currencyExchangers.add(new RublesToDollarsExchanger(rate));
        }
    }

    public List<CurrencyExchanger> getCurrencyExchangersByExpression(String expression) {
        return currencyExchangers
                .stream()
                .filter(currencyExchanger -> currencyExchanger.getPattern().matcher(expression).find())
                .collect(Collectors.toList());
    }
}
