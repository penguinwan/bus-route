package com.penguinwan.domain;

public class SimpleRouteFinder implements IRouteFinder {
    private IDataProvider dataProvider;

    public SimpleRouteFinder(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean isConnected(Station departure, Station arrival) {
        Iterable<Route> routes = dataProvider.iterator();
        for (Route route : routes) {
            boolean hasDeparture = false;
            boolean hasArrival = false;
            for (Station station : route.getStations()) {
                if (station.isSameStationWith(departure)) {
                    hasDeparture = true;
                }
                if (station.isSameStationWith(arrival)) {
                    hasArrival = true;
                }
            }
            if (hasDeparture && hasArrival) {
                return true;
            }
        }
        return false;
    }
}
