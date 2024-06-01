package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class DateCalculatorTest {
    @Test
    public void testPath1() {
        // Test case for Path 1
        DateCalculator.h(100, 1399); // Expected output: "4 8"
    }

    @Test
    public void testPath2() {
        // Test case for Path 2
        DateCalculator.h(200, 1399); // Expected output: "8 20"
    }

    @Test
    public void testPath3() {
        // Test case for Path 3 (leap year)
        DateCalculator.h(350, 1400); // Expected output: "12 14"
    }

    @Test
    public void testPath4() {
        // Test case for Path 4 (non-leap year)
        DateCalculator.h(350, 1399); // Expected output: "12 14"
    }

    @Test(expected = RuntimeException.class)
    public void testPath5() {
        // Test case for Path 5 (leap year, x out of range)
        DateCalculator.h(400, 1400); // Expected to throw RuntimeException
    }

    @Test(expected = RuntimeException.class)
    public void testPath6() {
        // Test case for Path 6 (non-leap year, x out of range)
        DateCalculator.h(400, 1399); // Expected to throw RuntimeException
    }
}