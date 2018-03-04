package com.penguinwan.infrastructure.util

import org.junit.Test

class NumericCheckerTest {
    @Test
    void "return false when integer"() {
        assert NumericChecker.isNonNumeric('1') == false
    }

    @Test
    void "return true when alphabet"() {
        assert NumericChecker.isNonNumeric('a') == true
    }

    @Test
    void "return true when symbol"() {
        assert NumericChecker.isNonNumeric('%') == true
    }

    @Test
    void "return true when decimal"() {
        assert NumericChecker.isNonNumeric('1.1') == true
    }
}
