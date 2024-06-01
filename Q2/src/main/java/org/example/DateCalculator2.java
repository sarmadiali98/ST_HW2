package org.example;

public class DateCalculator2 {
    public static boolean isLeapYear(int y) {
        // Dummy implementation
        return (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0));
    }

    public static void h(int x, int y) {
        if (x <= 6 * 31) {
            System.out.println((x / 31 + 1) + " " + (x % 31 + 1));
        } else {
            x -= 6 * 31;
            if (x <= 5 * 30) {
                System.out.println((7 + x / 30) + " " + (1 + x % 31));
            } else {
                x -= 5 * 30;
                boolean leap = isLeapYear(y);
                if (x <= 29) {
                    System.out.println(12 + " " + x);
                } else if (leap && x == 30) {
                    System.out.println(12 + " " + x);
                } else {
                    throw new RuntimeException();
                }
            }
        }
    }
}