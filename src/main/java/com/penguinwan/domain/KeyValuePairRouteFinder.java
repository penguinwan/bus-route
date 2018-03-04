package com.penguinwan.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyValuePairRouteFinder implements IRouteFinder {
    private Map<Station, StationWithRouteId> allStation;

    public KeyValuePairRouteFinder(IDataProvider dataProvider) {
        Iterable<Route> dataIterator = dataProvider.iterator();
        if (!dataIterator.iterator().hasNext()) {
            allStation = new HashMap<>();
        } else {
            allStation = combineData(dataProvider);

        }
    }

    @Override
    public boolean isConnected(Station departure, Station arrival) {

        StationWithRouteId departureStation = allStation.get(departure);
        StationWithRouteId arrivalStation = allStation.get(arrival);

        if (departureStation == null || arrivalStation == null) {
            return false;
        }

        for (Integer departureStationRouteId : departureStation.routeIds) {
            for (Integer arrivalStationRouteId : arrivalStation.routeIds) {
                if (departureStationRouteId.equals(arrivalStationRouteId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<Station, StationWithRouteId> combineData(IDataProvider dataProvider) {
        HashMap<Station, StationWithRouteId> stationMap = new HashMap();
        for (Route route : dataProvider.iterator()) {
            for (Station station : route.getStations()) {
                StationWithRouteId stationWithRouteId;
                if (stationMap.containsKey(station)) {
                    stationWithRouteId = stationMap.get(station);

                } else {
                    stationWithRouteId = new StationWithRouteId(station);
                    stationMap.put(station, stationWithRouteId);
                }
                stationWithRouteId.routeIds.add(route.getId());
            }
        }
        return stationMap;
    }

    class StationWithRouteId {
        private List<Integer> routeIds = new ArrayList<>();
        private Station station;

        StationWithRouteId(Station station) {
            this.station = station;
        }
    }
}
