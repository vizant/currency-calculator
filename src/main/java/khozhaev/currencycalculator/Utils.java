package khozhaev.currencycalculator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Utils {

    public static <T> List<T> getMatchedElementsForExpression(T[] array,
                                                              Function<T, String> stringExtractor,
                                                              String expression) {
        return Arrays.stream(array)
                .filter(elem -> expression.contains(stringExtractor.apply(elem)))
                .collect(Collectors.toList());
    }
}
