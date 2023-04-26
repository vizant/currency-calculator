package khozhaev.currencycalculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import khozhaev.currencycalculator.exhanger.CurrencyExchanger;
import khozhaev.currencycalculator.exhanger.CurrencyExchangerFactory;

public class Calculator {
    private final static Pattern EXPRESSION_PATTERN = Pattern.compile("(\\d+(\\.\\d+)?.)+\\d+(\\.\\d+)?");
    private final static Pattern NUMBER_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");

    private final CurrencyExchangerFactory currencyExchangerFactory;

    public Calculator(CurrencyExchangerFactory currencyExchangerFactory) {
        this.currencyExchangerFactory = currencyExchangerFactory;
    }

    public String calculate(String initialExpression) {
        String expression = prepareExpression(initialExpression);

        List<CurrencyExchanger> exchangers = currencyExchangerFactory.getCurrencyExchangersByExpression(initialExpression);

        for (CurrencyExchanger exchanger : exchangers) {
            expression = exchanger.exchange(initialExpression);
        }

        Currency currency = defineCurrency(expression);
        expression = expression.replaceAll(currency.getSign(), "");

        Matcher numberMatcher = NUMBER_PATTERN.matcher(expression);
        if (numberMatcher.matches()) {
            return currency.format(expression);
        }
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (matcher.find()) {
            String match = matcher.group(0);
            return processCurrencyOperations(match, currency);
        }

        throw new RuntimeException("Incorrect expression!");
    }

    private String processCurrencyOperations(String expression, Currency currency) {
        List<String> parts = new ArrayList<>();
        int currentIndex = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (!(Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                parts.add(expression.substring(currentIndex, i));
                parts.add(String.valueOf(expression.charAt(i)));
                currentIndex = i + 1;
            }
        }
        parts.add(expression.substring(currentIndex));

        double result = Double.parseDouble(parts.get(0));

        for (int i = 1; i < parts.size(); i += 2) {
            CurrencyOperation currencyOperation = defineCurrencyOperation(parts.get(i));
            double secondValue = Double.parseDouble(parts.get(i+1));
            result = currencyOperation.getFunction().applyAsDouble(result, secondValue);
        }

        return currency.format(result);
    }

    private Currency defineCurrency(String expression) {
        List<Currency> currencies =
                Utils.getMatchedElementsForExpression(Currency.values(), Currency::getSign, expression);
        if (currencies.size() == 0) {
            throw new RuntimeException("Currency undefined!");
        }
        if (currencies.size() > 1) {
            throw new RuntimeException("Too many currencies!");
        }
        return currencies.get(0);
    }

    private CurrencyOperation defineCurrencyOperation(String expression) {
        List<CurrencyOperation> currencyOperations =
                Utils.getMatchedElementsForExpression(
                        CurrencyOperation.values(), CurrencyOperation::getOperator, expression
                );
        if (currencyOperations.size() == 0) {
            throw new RuntimeException("Operation undefined!");
        }
        if (currencyOperations.size() > 1) {
            throw new RuntimeException("Too many operations!");
        }
        return currencyOperations.get(0);
    }

    private String prepareExpression(String expression) {
        return expression.replaceAll(",", ".").replaceAll("\\s", "");
    }
}
