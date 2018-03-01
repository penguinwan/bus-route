package com.penguinwan.domain;

public class RouteFinder {
    private IDataProvider dataProvider;

    public RouteFinder(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public boolean isConnected(Station departure, Station arrival) {
        Iterable<Route> routes = dataProvider.iterator();
        for (Route route : routes) {
            boolean hasDeparture = false;
            boolean hasArrival = false;
            for (Station station : route.getStations()) {
                if (station.getId() == departure.getId()) {
                    hasDeparture = true;
                }
                if (station.getId() == arrival.getId()) {
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
