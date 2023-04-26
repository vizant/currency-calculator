package khozhaev.currencycalculator.exhanger;

import java.util.regex.Pattern;

import khozhaev.currencycalculator.Currency;

public class DollarsToRublesExchanger extends CurrencyExchanger {

    private final static Pattern rublesPattern = Pattern.compile("toRubles(\\(\\$\\d+(\\.\\d+)?\\))");

    public DollarsToRublesExchanger(double rate) {
        super(rate);
    }

    @Override
    public Pattern getPattern() {
        return rublesPattern;
    }

    @Override
    public String extractCurrencyValue(String expression) {
        int startIndex = expression.indexOf("($") + 2;
        int endIndex = expression.indexOf(")");
        return expression.substring(startIndex, endIndex);
    }

    @Override
    public String formatReplacement(Double result) {
        return Currency.RUBLE.format(result);
    }
}
