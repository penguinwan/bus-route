package com.penguinwan.domain;

/**
 * Station.
 */
public class Station {
    private int id;

    private Station() {
    }

    /**
     * Factory method to create station.
     *
     * @param id station id
     * @return
     */
    public static Station of(int id) {
        Station station = new Station();
        station.id = id;
        return station;
    }

    /**
     * To check if the given station is the same with this station.
     *
     * @param station
     * @return
     */
    public boolean isSameStationWith(Station station) {
        if (station == null) return false;
        return this.id == station.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        return id == station.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
