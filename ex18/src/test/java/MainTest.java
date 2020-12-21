import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    private void tst(String s, int num) {
        assertEquals(num, Main.calc(s));
    }

    @Test
    void tstDigit() {
        tst("2", 2);
        tst("12", 12);
        tst("987", 987);
    }

    @Test
    void tstDigitClose() {
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
}
