package com.penguinwan.domain;

public class Station {
    private int id;

    public static Station of(int id) {
        Station station = new Station();
        station.id = id;
        return station;
    }

    public boolean isSameStationWith(Station station) {
        if (station == null) return false;
        return this.id == station.id;
    }
}
