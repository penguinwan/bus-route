package com.penguinwan.domain;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class SimpleRouteFinder implements IRouteFinder {
    private IDataProvider dataProvider;

    public SimpleRouteFinder(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean isConnected(Station departure, Station arrival) {
        Predicate<Station> isDepartureStation = (station) -> station.isSameStationWith(departure);
        Predicate<Station> isArrivalStation = (station) -> station.isSameStationWith(arrival);

        Predicate<Route> hasDepartureAndArrival =
                (route) -> route.
                        getStations().
                        stream().
                        filter(isDepartureStation.or(isArrivalStation)).
                        count() == 2;

        return StreamSupport.stream(dataProvider.iterator().spliterator(), false).
                filter(hasDepartureAndArrival).
                count() > 0;

    }
}
