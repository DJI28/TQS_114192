package calculator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import static java.util.Arrays.asList;

public class Calculator {
    private final Deque<Number> stack = new LinkedList<>();
    private static final List<String> OPS = asList("-", "+", "*", "/");
    private boolean errorState = false;

    public void push(Object arg) {
        if (OPS.contains(arg)) {
            Number y = stack.removeLast();
            Number x = stack.isEmpty() ? 0 : stack.removeLast();
            Double val = null;
            if (arg.equals("-")) {
                val = x.doubleValue() - y.doubleValue();
            } else if (arg.equals("+")) {
                val = x.doubleValue() + y.doubleValue();
            } else if (arg.equals("*")) {
                val = x.doubleValue() * y.doubleValue();
            } else if (arg.equals("/")) {
                if (y.doubleValue() == 0) {
                    errorState = true;
                    return;
                }
                val = x.doubleValue() / y.doubleValue();
            }
            push(val);
        } else {
            stack.add((Number) arg);
        }
    }

    public Number value() {
        if (errorState) {
            throw new ArithmeticException("Division by zero");
        }
        return stack.getLast();
    }
}
