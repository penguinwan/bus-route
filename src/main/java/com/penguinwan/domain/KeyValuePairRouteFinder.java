package com.penguinwan.domain;

import java.util.*;

/**
 * This class utilizes map for quicker searching.
 * Data from dataProvider will be stored in a map.
 */
public class KeyValuePairRouteFinder implements IRouteFinder {
    private Map<Station, StationWithRouteId> allStation;

    /**
     * Constructor. Will read data from dataProvider and store locally.
     *
     * @param dataProvider
     */
    public KeyValuePairRouteFinder(IDataProvider dataProvider) {
        Iterable<Route> dataIterator = dataProvider.iterator();
        if (!dataIterator.iterator().hasNext()) {
            allStation = new HashMap<>();
        } else {
            allStation = combineAllStation(dataProvider);

        }
    }

    @Override
    public boolean isConnected(Station departure, Station arrival) {

        StationWithRouteId departureStation = allStation.get(departure);
        StationWithRouteId arrivalStation = allStation.get(arrival);

        if (departureStation == null || arrivalStation == null) {
            return false;
        }

        Optional<Integer> commonRoute = departureStation.
                routeIds.
                stream().
                filter((departureRouteId) -> arrivalStation.hasRouteId(departureRouteId)).
                findFirst();
        return commonRoute.isPresent();

    }

    private Map<Station, StationWithRouteId> combineAllStation(IDataProvider dataProvider) {
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

        boolean hasRouteId(int routeId) {
            Optional<Integer> result = routeIds.stream().filter((each) -> each == routeId).findFirst();
            return result.isPresent();
        }
    }
}
