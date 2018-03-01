package com.penguinwan.domain;

public class Station {
    private int id;

    public int getId() {
        return id;
    }

    public static Station of(int id) {
        Station station = new Station();
        station.id = id;
        return station;
    }
}
