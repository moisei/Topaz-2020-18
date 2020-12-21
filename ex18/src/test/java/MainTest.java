import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    private void tst(String s, int num) {
        assertEquals(num, MainTokens.calc(s));
    }

    @Test
    void tstNormalize() {
        assertEquals("((1) +2)".replaceAll("\\s", ""), "((1)+2)");
    }

    @Test
    void tstDigit() {
        tst("2", 2);
        tst("12", 12);
        tst("987", 987);
    }

    @Test
    void tstDigitBracket() {
        tst("(12)", 12);
        tst("(0)", 0);
    }

    @Test
    void tstNumAndOp() {
        tst("1+2", 3);
        tst("(1+12)", 13);
        tst("(12+123)", 135);
        tst("(123+98)", 221);
    }

    @Test
    void tstBracketAndExpr() {
        tst("(1)+(3)", 4);
        tst("(1)+2", 3);
        tst("(12)+3", 15);
        tst("(12)+(3)", 15);
    }

    @Test
    void tstEmbed() {
        tst("((1))", 1);
        tst("((1) +2)", 3);
        tst("9 + ((1) +2) + 3 + (4)", 19);
    }

    @Test
    void tstMult() {
        tst("1*2+3", 5);
        tst("((1) +2)", 3);
    }

    @Test
    void tstComb() {
        tst("3*4+5", 17);
    }

    @Test
    void tstExamples() {
        tst("2 * 3 + (4 * 5)", 26);
        tst("5 + (8 * 3 + 9 + 3 * 4 * 3)", 437);
        tst("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 12240);
        tst("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 13632);
    }
}
