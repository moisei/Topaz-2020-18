// https://adventofcode.com/2020/day/18?fbclid=IwAR0gGFZPJDCon8LISoaBVn1t-lQ6RiN8P9GmPr4OHqCeHDdwLc6CF4gFXZk

public class Main {
    public static long calc(String expr) {
        return calc(normalize(expr), 0, expr.length() - 1);
    }

    // the valid expr can begin either with the ( or with the digit.
    // if started with the digit then it's form is  is num [OP rightExpr]
    // if started with the (     then it's form is  ( innerExpr ) [OP rightExpr]
    public static long calc(char[] expr, int firstIdx, int lastIdx) {
        if ('(' == expr[firstIdx]) {
            int newLastIdx = lastIdx - 1;
            for (int i = lastIdx; i > firstIdx && expr[i] != ')'; --i) {
                newLastIdx = i;
            }
            long innerExpr = calc(expr, firstIdx + 1, newLastIdx);
            return innerExpr;
        } else {
            int newFirstIdx = firstIdx;
            for (int i = firstIdx; i <= lastIdx && isDigit(expr[i]); ++i) {
                newFirstIdx = i;
            }
            long num = Long.parseLong(new String(expr, firstIdx, newFirstIdx - firstIdx + 1));
            if (newFirstIdx == lastIdx) {
                return num;
            }
            char op = expr[newFirstIdx + 1];
            long rightExpr = calc(expr, newFirstIdx + 2, lastIdx);
            return applyOp(op, num, rightExpr);
        }
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

    private static char[] normalize(String expr) {
        expr = expr.replace(" ", "");
        return expr.toCharArray();
    }

}
