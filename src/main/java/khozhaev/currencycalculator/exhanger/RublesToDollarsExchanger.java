package khozhaev.currencycalculator.exhanger;

import java.util.regex.Pattern;

import khozhaev.currencycalculator.Currency;

public class RublesToDollarsExchanger extends CurrencyExchanger {

    private final static Pattern dollarsPattern = Pattern.compile("toDollars(\\(\\d+(\\.\\d+)?p\\))");

    public RublesToDollarsExchanger(double rate) {
        super(rate);
    }

    @Override
    public Pattern getPattern() {
        return dollarsPattern;
    }

    @Override
    public String extractCurrencyValue(String expression) {
        int startIndex = expression.indexOf("(") + 1;
        int endIndex = expression.indexOf("p)") + 1;
        return expression.substring(startIndex, endIndex);
    }

    @Override
    public String formatReplacement(Double result) {
        return Currency.DOLLAR.format(result);
    }
}
