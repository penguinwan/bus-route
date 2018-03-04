package com.penguinwan.infrastructure

import com.penguinwan.domain.Route
import org.junit.Test

import java.nio.file.Path
import java.nio.file.Paths

class StreamingDataProviderTest {
    @Test
    void "return correct result when file path and content is correct"() {
        URL url = StreamingDataProviderTest.class.getResource('valid-data')
        Path path = Paths.get(url.toURI())
        StreamingDataProvider dataProvider = new StreamingDataProvider(path)

        List<Route> routes = new ArrayList()
        for (Route route : dataProvider.iterator()) {
            routes.add(route)
        }

        assert routes[0].id == 1
        assert routes[0].stations[0].id == 1
        assert routes[0].stations[1].id == 2
        assert routes[0].stations[2].id == 3
        assert routes[1].id == 2
        assert routes[1].stations[0].id == 4
        assert routes[1].stations[1].id == 5
        assert routes[1].stations[2].id == 6
        assert routes[2].id == 3
        assert routes[2].stations[0].id == 7
        assert routes[2].stations[1].id == 8
        assert routes[2].stations[2].id == 9
    }

    @Test
    void "it is ok to stream again and again"() {
        URL url = StreamingDataProviderTest.class.getResource('valid-data')
        Path path = Paths.get(url.toURI())
        StreamingDataProvider dataProvider = new StreamingDataProvider(path)

        List<Route> firstRead = new ArrayList()
        for (Route route : dataProvider.iterator()) {
            firstRead.add(route)
        }

        assert firstRead[0].id == 1
        assert firstRead[0].stations[0].id == 1
        assert firstRead[0].stations[1].id == 2
        assert firstRead[0].stations[2].id == 3

        List<Route> secondRead = new ArrayList()
        for (Route route : dataProvider.iterator()) {
            secondRead.add(route)
        }

        assert secondRead[0].id == 1
        assert secondRead[0].stations[0].id == 1
        assert secondRead[0].stations[1].id == 2
        assert secondRead[0].stations[2].id == 3
    }

    @Test
    void "return empty iterator when the route data is empty"() {
        URL url = StreamingDataProviderTest.class.getResource('empty-data')
        Path path = Paths.get(url.toURI())
        StreamingDataProvider dataProvider = new StreamingDataProvider(path)
        assert dataProvider.iterator().iterator().hasNext() == false
    }

    @Test(expected = InvalidFormatException.class)
    void "error when file is empty"() {
        URL url = StreamingDataProviderTest.class.getResource('empty-file')
        Path path = Paths.get(url.toURI())
        new StreamingDataProvider(path)
    }

    @Test(expected = IllegalArgumentException.class)
    void "error when path is null"() {
        new StreamingDataProvider(null)
    }

    @Test(expected = InvalidFileException.class)
    void "error when file does not exist"() throws Exception {
        String invalidFilePath = "/non/exist.txt";
        new StreamingDataProvider(Paths.get(invalidFilePath));
    }

    @Test(expected = InvalidFormatException.class)
    void "error when number of routes is non-numeric"() {
        URL url = StreamingDataProviderTest.class.getResource('invalid-number-of-routes')
        Path path = Paths.get(url.toURI())
        new StreamingDataProvider(path)
    }
}
