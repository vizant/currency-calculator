package khozhaev.currencycalculator;

import java.util.function.UnaryOperator;

public enum Currency {
    RUBLE(
            "p",
            (s) -> s + "p"
    ),
    DOLLAR(
            "$",
            (s) -> "$" + s
    );

    private final String sign;
    private final UnaryOperator<String> stringFormatter;

    Currency(String sign, UnaryOperator<String> stringFormatter) {
        this.sign = sign;
        this.stringFormatter = stringFormatter;
    }

    public String getSign() {
        return sign;
    }

    public String format(Double d) {
        String s = String.valueOf(d);
        return stringFormatter.apply(s);
    }

    public String format(String s) {
        return stringFormatter.apply(s);
    }
}
