package com.penguinwan.domain

import org.junit.Test

class SimpleRouteFinderTest {
    SimpleRouteFinder routeFinder;

    @Test
    void "able to find first result"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(1), Station.of(3)) == true
    }

    @Test
    void "able to find last result"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3],
                [2, 4, 5, 6],
                [3, 7, 8, 9]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(7), Station.of(9)) == true
    }

    @Test
    void "able to find adjacent station"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3, 4, 5, 6, 7, 8]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(6), Station.of(7)) == true
    }

    @Test
    void "able to find reverse order"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3, 4, 5, 6, 7, 8]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(8), Station.of(4)) == true
    }

    @Test
    void "able to return false when there is no station"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(4), Station.of(6)) == false
    }

    @Test
    void "able to return false when only departure station is found"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(1), Station.of(6)) == false
    }

    @Test
    void "able to return false when only arrival station is found"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(6), Station.of(3)) == false
    }

    @Test
    void "return false when departure station is not specified"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(null, Station.of(3)) == false
    }

    @Test
    void "return false when arrival station is not specified"() {
        IDataProvider dataProvider = dataProviderOf([
                [1, 1, 2, 3]
        ])

        routeFinder = new SimpleRouteFinder(dataProvider)

        assert routeFinder.isConnected(Station.of(1), null) == false
    }

    IDataProvider dataProviderOf(List<List<int[]>> data) {
        return {
            List<Route> routes = new ArrayList<>()
            data.each { routeLine ->
                List<Station> stations = new ArrayList<>()
                routeLine.eachWithIndex { stationNumber, index ->
                    if (index != 0) {
                        stations.add(Station.of(stationNumber))
                    }
                }
                routes.add(new Route(routeLine[0], stations))
            }
            return routes
        } as IDataProvider
    }
}
