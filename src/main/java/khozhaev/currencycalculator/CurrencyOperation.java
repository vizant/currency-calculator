package khozhaev.currencycalculator;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

public enum CurrencyOperation {
    ADD("+", (x, y) -> x + y),
    SUBTRACT("-", (x, y) -> x - y);

    private final String operator;
    private final DoubleBinaryOperator function;

    CurrencyOperation(String operator, DoubleBinaryOperator function) {
        this.operator = operator;
        this.function = function;
    }

    public String getOperator() {
        return operator;
    }

    public DoubleBinaryOperator getFunction() {
        return function;
    }

    public static Optional<CurrencyOperation> getCurrencyOperationByOperator(String operator) {
        return Arrays.stream(values())
                .filter(currencyOperation -> currencyOperation.getOperator().equals(operator))
                .findFirst();
    }
}
