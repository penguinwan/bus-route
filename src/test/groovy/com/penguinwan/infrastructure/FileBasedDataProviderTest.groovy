package com.penguinwan.infrastructure

import com.penguinwan.domain.Route
import org.junit.Test

import java.nio.file.Path
import java.nio.file.Paths

class FileBasedDataProviderTest {
    @Test
    void "return correct result when file path and content is correct"() {
        URL url = FileBasedDataProviderTest.class.getResource('valid-data')
        Path path = Paths.get(url.toURI())
        FileBasedDataProvider dataProvider = new FileBasedDataProvider(path)

        dataProvider.iterator()[0].id == 1
        dataProvider.iterator()[0].stations[0] == 1
        dataProvider.iterator()[0].stations[1] == 2
        dataProvider.iterator()[0].stations[2] == 3
        dataProvider.iterator()[1].id == 2
        dataProvider.iterator()[1].stations[0] == 4
        dataProvider.iterator()[1].stations[1] == 5
        dataProvider.iterator()[1].stations[2] == 6
        dataProvider.iterator()[2].id == 3
        dataProvider.iterator()[2].stations[0] == 7
        dataProvider.iterator()[2].stations[1] == 8
        dataProvider.iterator()[2].stations[2] == 9
    }

    @Test(expected = IllegalArgumentException.class)
    void "error when path is null"() {
        new FileBasedDataProvider(null)
    }

    @Test(expected = InvalidFileException.class)
    void "error when file does not exist"() throws Exception {
        String invalidFilePath = "/non/exist.txt";
        new FileBasedDataProvider(Paths.get(invalidFilePath));
    }

    @Test
    void "able to parse well-format string"() {
        Route route = FileBasedDataProvider.LineParser.parse('10 1 2 3')

        assert route.id == 10
        assert route.stations[0].id == 1
        assert route.stations[1].id == 2
        assert route.stations[2].id == 3
    }

    @Test
    void "able to parse with leading space"() {
        Route route = FileBasedDataProvider.LineParser.parse(' 10 1 2 3')

        assert route.id == 10
        assert route.stations[0].id == 1
        assert route.stations[1].id == 2
        assert route.stations[2].id == 3
    }

    @Test
    void "able to parse with trailing space"() {
        Route route = FileBasedDataProvider.LineParser.parse('10 1 2 3 ')

        assert route.id == 10
        assert route.stations[0].id == 1
        assert route.stations[1].id == 2
        assert route.stations[2].id == 3
    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when route id is non-numeric character"() {
        Route route = FileBasedDataProvider.LineParser.parse('a 1 2 3')

    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when station id is non-numeric character"() {
        Route route = FileBasedDataProvider.LineParser.parse('1 * 2 3')

    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when there is no station id"() {
        Route route = FileBasedDataProvider.LineParser.parse('1')
    }

    @Test(expected = InvalidFormatException.class)
    void "not able to parse when there is no route id"() {
        Route route = FileBasedDataProvider.LineParser.parse('')
    }
}
