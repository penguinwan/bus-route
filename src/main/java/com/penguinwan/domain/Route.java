package com.penguinwan.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Route that contains station that it is traveling.
 */
public class Route {
    private int id;
    private List<Station> stations = new ArrayList<>();

    /**
     * Constructor with route id. The resulted station list will be empty.
     *
     * @param id
     */
    public Route(int id) {
        this.id = id;
    }

    /**
     * Constructor with route id, and stations.
     *
     * @param id
     * @param stations
     */
    public Route(int id, List<Station> stations) {
        this(id);
        this.stations.addAll(stations);
    }

    /**
     * Route id
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Stations of this route.
     *
     * @return its station or empty list when there is no stations
     */
    public List<Station> getStations() {
        return stations;
    }
}
