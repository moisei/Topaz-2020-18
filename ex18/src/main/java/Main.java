public class Main {
    public static long calc(String expr) {
        return calc(expr.toCharArray(), 0, expr.length() - 1);
    }

    public static long calc(char[] expr, int beg, int end) {
        char c = expr[beg];
        if ('(' == c) {
            int newEnd = end;
            for (int i = end; i > beg && i != ')'; --i) {
                newEnd = i;
            }
            long innerExpr = calc(expr, beg + 1, newEnd + 1);
            return innerExpr;
        } else {
            int newBeg = beg;
            for (int i = beg; i <= end && isDigit(expr[i]); ++i) {
                newBeg = i;
            }
            long num = Long.parseLong(new String(expr, beg, newBeg - beg + 1));
            return num;
        }
    }

    private static boolean isDigit(char i) {
        return i >= '0' && i <= '9';
    }

    //    interface Token {
//        TokenType  getType();
//        TokenValue =
//    }
//
//    private static List<Token> tokenize(String expr) {
//        Token.
//        return lst;
//    }
//
    private static String trim(String expr) {
        return expr;
    }
}
