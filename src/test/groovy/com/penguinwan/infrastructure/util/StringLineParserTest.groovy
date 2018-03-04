package com.penguinwan.infrastructure.util

import com.penguinwan.domain.Route
import com.penguinwan.infrastructure.InvalidFormatException
import org.junit.Test

class StringLineParserTest {

    @Test
    void "able to parse well-format string"() {
        Route route = StringLineParser.parse('10 1 2 3')

        assert route.id == 10
        assert route.stations[0].id == 1
        assert route.stations[1].id == 2
        assert route.stations[2].id == 3
    }

    @Test
    void "able to parse with leading space"() {
        Route route = StringLineParser.parse(' 10 1 2 3')

        assert route.id == 10
        assert route.stations[0].id == 1
        assert route.stations[1].id == 2
        assert route.stations[2].id == 3
    }

    @Test
    void "able to parse with trailing space"() {
        Route route = StringLineParser.parse('10 1 2 3 ')

        assert route.id == 10
        assert route.stations[0].id == 1
        assert route.stations[1].id == 2
        assert route.stations[2].id == 3
    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when route id is non-numeric character"() {
        Route route = StringLineParser.parse('a 1 2 3')

    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when station id is non-numeric character"() {
        Route route = StringLineParser.parse('1 * 2 3')

    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when there is no station id"() {
        Route route = StringLineParser.parse('1')
    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when there is no route id"() {
        Route route = StringLineParser.parse('')
    }
}
