// https://adventofcode.com/2020/day/18?fbclid=IwAR0gGFZPJDCon8LISoaBVn1t-lQ6RiN8P9GmPr4OHqCeHDdwLc6CF4gFXZk

class Calculator {
    private final char[] expr;

    public long calc() {
        return calc(0, expr.length - 1);

    }

    public Calculator(char[] expr) {
        this.expr = expr;
    }

    public long calc(int firstIdx, int lastIdx) {
        if ('(' == expr[firstIdx]) {
            int innerLastIdx = -1 + findMatchingCloseBracketIdx(firstIdx + 1, lastIdx);
            long currValue = calc(firstIdx + 1, innerLastIdx);
            if (innerLastIdx == lastIdx - 1) {
                return currValue;
            }
            char op = expr[innerLastIdx + 2];
            return calc(currValue, op, innerLastIdx + 3, lastIdx);
        } else {
            int newFirstIdx = findNextFirstIdx(firstIdx, lastIdx);
            String numStr = new String(expr, firstIdx, newFirstIdx - firstIdx);
            long currValue = Long.parseLong(numStr);
            if (newFirstIdx == 1 + lastIdx) {
                return currValue;
            }
            char op = expr[newFirstIdx];
            return calc(currValue, op, newFirstIdx + 1, lastIdx);
        }
    }

    private long calc(long currValue, char op, int firstIdx, int lastIdx) {
        if ('(' == expr[firstIdx]) {
            int innerLastIdx = -1 + findMatchingCloseBracketIdx(firstIdx + 1, lastIdx);
            long newCurrValue = applyOp(op, currValue, calc(firstIdx + 1, innerLastIdx));
            if (innerLastIdx == lastIdx - 1) {
                return newCurrValue;
            }
            char newOp = expr[innerLastIdx + 2];
            return calc(newCurrValue, newOp, innerLastIdx + 3, lastIdx);
        } else {
            int newFirstIdx = findNextFirstIdx(firstIdx, lastIdx);
            currValue = applyOp(op, currValue, Long.parseLong(new String(expr, firstIdx, newFirstIdx - firstIdx)));
            if (newFirstIdx == 1 + lastIdx) {
                return currValue;
            }
            char newOp = expr[newFirstIdx];
            return calc(currValue, newOp, newFirstIdx + 1, lastIdx);
        }
    }

    private int findNextFirstIdx(int firstIdx, int lastIdx) {
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

    private static long applyOp(char op, long num1, long num2) {
        switch (op) {
            case '+' -> {
                return num1 + num2;
            }
            case '*' -> {
                return num1 * num2;
            }
            default -> throw new RuntimeException("Unknown operator: " + op);
        }
    }

    private static boolean isDigit(char i) {
        return i >= '0' && i <= '9';
    }
}

public class MainTokens {
    public static long calc(String expr) {
        char[] normalizedExpr = normalize(expr);
        return new Calculator(normalizedExpr).calc();
    }

    private static char[] normalize(String expr) {
        expr = expr.replaceAll("\\s", "");
        return expr.toCharArray();
    }

}


