import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartTwoTest {
    private void tst(String s, int num) {
        assertEquals(num, PartTwo.calc(s));
    }

    @Test
    void tstNormalize() {
        assertEquals("((1)\n +2)".replaceAll("\\s", "").replaceAll("\\n", ""), "((1)+2)");
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
    void testExamples() {
        tst("1 + (2 * 3) + (4 * (5 + 6))", 51);
        tst("2 * 3 + (4 * 5)", 46);
        tst("5 + (8 * 3 + 9 + 3 * 4 * 3)", 1445);
        tst("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 669060);
        tst("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 23340);
    }
}
