// https://adventofcode.com/2020/day/18?fbclid=IwAR0gGFZPJDCon8LISoaBVn1t-lQ6RiN8P9GmPr4OHqCeHDdwLc6CF4gFXZk

@SuppressWarnings("SpellCheckingInspection")
class Calculator2 {
    private static final char NOOP = 0;
    private static final long NOVALUE = Long.MIN_VALUE;
    private final char[] expr;

    public Calculator2(char[] expr) {
        this.expr = expr;
    }

    public long calc() {
        return calc(NOVALUE, NOOP, 0, expr.length - 1);

    }

    private long calc(long prevValue, char op, int firstIdx, int lastIdx) {
        if (op == '*') {
            return prevValue * calc(NOVALUE, NOOP, firstIdx, lastIdx);
        }
        if ('(' == expr[firstIdx]) {
            int innerLastIdx = -1 + findMatchingCloseBracketIdx(firstIdx + 1, lastIdx);
            long innerValue = calc(NOVALUE, NOOP, firstIdx + 1, innerLastIdx);
            long newCurrValue = applyOp(op, innerValue, prevValue);
            boolean endOfExp = innerLastIdx == lastIdx - 1;
            if (endOfExp) {
                return newCurrValue;
            }
            char newOp = expr[innerLastIdx + 2];
            return calc(newCurrValue, newOp, innerLastIdx + 3, lastIdx);
        } else {
            int nextTokenIdx = findNextNonDigitIdx(firstIdx, lastIdx);
            long innerValue = Long.parseLong(new String(expr, firstIdx, nextTokenIdx - firstIdx));
            long newCurrValue = applyOp(op, innerValue, prevValue);
            if (nextTokenIdx == 1 + lastIdx) {
                return newCurrValue;
            }
            char newOp = expr[nextTokenIdx];
            return calc(newCurrValue, newOp, nextTokenIdx + 1, lastIdx);
        }
    }

    private int findNextNonDigitIdx(int firstIdx, int lastIdx) {
        for (int i = firstIdx; i <= lastIdx; ++i) {
            if (!isDigit(expr[i])) {
                return i;
            }
        }
        return lastIdx + 1;
    }

    private int findMatchingCloseBracketIdx(int firstIdx, int lastIdx) {
        int cnt = 1;
        for (int i = firstIdx; i <= lastIdx; ++i) {
            switch (expr[i]) {
                case '(':
                    ++cnt;
                    break;
                case ')':
                    --cnt;
                    if (0 == cnt) {
                        return i;
                    }
                    break;
                default:
                    break;
            }
        }
        throw new RuntimeException("Brackets mismatch");
    }

    private static long applyOp(char op, long value1, long value2) {
        switch (op) {
            case NOOP -> {
                return value1;
            }
            case '+' -> {
                return value1 + value2;
            }
            case '*' -> {
                return value1 * value2;
            }
            default -> throw new RuntimeException("Unknown operator: " + op);
        }
    }

    private static boolean isDigit(char i) {
        return i >= '0' && i <= '9';
    }
}

public class PartTwo {
    public static long calcMultiline(String multiline) {
        String[] lines = multiline.split("\n");
        long sum = 0;
        for (String line : lines) {
            line = normalize(line);
            if (line.isEmpty()) {
                continue;
            }
            sum += new Calculator2(line.toCharArray()).calc();
        }
        return sum;
    }

    public static long calc(String expr) {
        char[] normalizedExpr = normalize(expr).toCharArray();
        return new Calculator2(normalizedExpr).calc();
    }

    private static String normalize(String expr) {
        return expr.replaceAll("\\s", "");
    }

}


