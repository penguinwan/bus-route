package com.penguinwan.infrastructure

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
        dataProvider.iterator()[0].stations[0].id == 1
        dataProvider.iterator()[0].stations[1].id == 2
        dataProvider.iterator()[0].stations[2].id == 3
        dataProvider.iterator()[1].id == 2
        dataProvider.iterator()[1].stations[0].id == 4
        dataProvider.iterator()[1].stations[1].id == 5
        dataProvider.iterator()[1].stations[2].id == 6
        dataProvider.iterator()[2].id == 3
        dataProvider.iterator()[2].stations[0].id == 7
        dataProvider.iterator()[2].stations[1].id == 8
        dataProvider.iterator()[2].stations[2].id == 9
    }

    @Test
    void "return empty iterator when the route data is empty"() {
        URL url = FileBasedDataProviderTest.class.getResource('empty-data')
        Path path = Paths.get(url.toURI())
        FileBasedDataProvider dataProvider = new FileBasedDataProvider(path)
        assert dataProvider.iterator().iterator().hasNext() == false
    }

    @Test(expected = InvalidFormatException.class)
    void "error when file is empty"() {
        URL url = FileBasedDataProviderTest.class.getResource('empty-file')
        Path path = Paths.get(url.toURI())
        new FileBasedDataProvider(path)
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

    @Test(expected = InvalidFormatException.class)
    void "error when number of routes is non-numeric"() {
        URL url = FileBasedDataProviderTest.class.getResource('invalid-number-of-routes')
        Path path = Paths.get(url.toURI())
        new FileBasedDataProvider(path)
    }

}
