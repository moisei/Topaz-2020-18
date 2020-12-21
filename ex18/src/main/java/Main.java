// https://adventofcode.com/2020/day/18?fbclid=IwAR0gGFZPJDCon8LISoaBVn1t-lQ6RiN8P9GmPr4OHqCeHDdwLc6CF4gFXZk

public class Main {
    public static long calc(String expr) {
        char[] normalizedExpr = normalize(expr);
        return calc(normalizedExpr, 0, normalizedExpr.length - 1);
    }

    // the valid expr can begin either with the ( or with the digit.
    public static long calc(char[] expr, int firstIdx, int lastIdx) {
        if ('(' == expr[firstIdx]) {
            int innerLastIdx = -1 + getMatchingCloseBracketIdx(expr, firstIdx + 1, lastIdx);
            long innerExpr = calc(expr, firstIdx + 1, innerLastIdx);
            if (innerLastIdx == lastIdx - 1) {
                return innerExpr;
            }
            char op = expr[innerLastIdx + 2];
            long rightExpr = calc(expr, innerLastIdx + 3, lastIdx);
            return applyOp(op, innerExpr, rightExpr);
        } else {
            int newFirstIdx = findNextFirstIdx(expr, firstIdx, lastIdx);
            String numStr = new String(expr, firstIdx, newFirstIdx - firstIdx);
            long num = Long.parseLong(numStr);
            if (newFirstIdx == 1 + lastIdx) {
                return num;
            }
            char op = expr[newFirstIdx];
            long rightExpr = calc(expr, newFirstIdx + 1, lastIdx);
            return applyOp(op, num, rightExpr);
        }
    }

    private static int findNextFirstIdx(char[] expr, int firstIdx, int lastIdx) {
        for (int i = firstIdx; i <= lastIdx; ++i) {
            if (!isDigit(expr[i])) {
                return i;
            }
        }
        return lastIdx + 1;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static int getMatchingCloseBracketIdx(char[] expr, int firstIdx, int lastIdx) {
        int cnt = 1;
        for (int i = firstIdx; i <= lastIdx; ++i) {
            if (expr[i] == ')') {
                --cnt;
                if (0 == cnt) {
                    return i;
                }
            } else if (expr[i] == '(') {
                ++cnt;
            } else {
                // skip
            }
        }
//
//        for (int i = lastIdx; i > firstIdx; --i) {
//            if (expr[i] == ')') {
//                return i - 1;
//            }
//        }
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

    private static char[] normalize(String expr) {
        expr = expr.replaceAll("\\s", "");
        return expr.toCharArray();
    }

}
