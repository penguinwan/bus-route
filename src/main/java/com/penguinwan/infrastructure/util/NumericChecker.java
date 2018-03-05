package com.penguinwan.infrastructure.util;

/**
 * To check if particular string is numeric.
 * Decimal is NOT numeric.
 */
public final class NumericChecker {
    /**
     * Nobody can instantiate this class.
     */
    private NumericChecker() {
    }

    /**
     * return true if given string is numeric, false if given string is not numeric
     *
     * @param s
     * @return
     */
    public static boolean isNonNumeric(String s) {
        return isNonNumeric(s, 10);
    }

    private static boolean isNonNumeric(String s, int radix) {
        if (s == null || s.isEmpty()) return true;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return true;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return true;
        }
        return false;
    }
}
