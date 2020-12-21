import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    void tstDigit() {
        assertEquals(0, Main.calc("0"));
        assertEquals(1, Main.calc("1"));
        assertEquals(2, Main.calc("2"));
        assertEquals(12, Main.calc("12"));
        assertEquals(987, Main.calc("987"));
        for (int i = 0; i < 1000; ++i) {
            assertEquals(i, Main.calc(String.valueOf(i)));
        }
    }

    @Test
    void tstDigitClose() {
        assertEquals(0, Main.calc("(0)"));
        assertEquals(12, Main.calc("(12)"));
    }
}
