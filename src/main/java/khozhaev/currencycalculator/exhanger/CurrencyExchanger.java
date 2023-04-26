package khozhaev.currencycalculator.exhanger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CurrencyExchanger {

    protected final double rate;

    public CurrencyExchanger(double rate) {
        this.rate = rate;
    }

    public String exchange(String expression) {
        Matcher matcher = getPattern().matcher(expression);
        while (matcher.find()) {
            String currencyExpression = matcher.group();
            String currencyValue = extractCurrencyValue(currencyExpression);
            double result = Double.parseDouble(currencyValue) * rate;
            expression = expression.replace(currencyExpression, formatReplacement(result));
        }
        return expression;
    }

    public abstract Pattern getPattern();

    public abstract String extractCurrencyValue(String expression);

    public abstract String formatReplacement(Double result);
}
