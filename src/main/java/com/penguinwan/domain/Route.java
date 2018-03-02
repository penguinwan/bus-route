package com.penguinwan.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private int id;
    private List<Station> stations = new ArrayList<Station>();

    public Route(int id) {
        this.id = id;
    }

    public Route(int id, List<Station> stations) {
        this(id);
        this.stations.addAll(stations);
    }

    public int getId() {
        return id;
    }

    public List<Station> getStations() {
        return stations;
    }
}
