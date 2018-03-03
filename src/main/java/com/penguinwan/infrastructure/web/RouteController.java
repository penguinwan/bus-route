package com.penguinwan.infrastructure.web;

import com.penguinwan.domain.IRouteFinder;
import com.penguinwan.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api", produces = "application/json")
public class RouteController {

    public static final String PARAM_DEPARTURE_STATION_ID = "dep_sid";
    public static final String PARAM_ARRIVAL_STATION_ID = "arr_sid";
    public static final String PARAM_DIRECT_BUS_ROUTE = "direct_bus_route";

    @Autowired
    IRouteFinder routeFinder;

    @GetMapping("/direct")
    public ResponseEntity<Map> get(@RequestParam(name = PARAM_DEPARTURE_STATION_ID) int departureStationId,
                                   @RequestParam(name = PARAM_ARRIVAL_STATION_ID) int arrivalStationId) {

        boolean result = routeFinder.isConnected(Station.of(departureStationId), Station.of(arrivalStationId));
        return ResponseEntity.ok(resourceFrom(result, departureStationId, arrivalStationId));
    }

    Map resourceFrom(boolean isConnected, int departureStationId, int arrivalStationId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(PARAM_DEPARTURE_STATION_ID, departureStationId);
        result.put(PARAM_ARRIVAL_STATION_ID, arrivalStationId);
        result.put(PARAM_DIRECT_BUS_ROUTE, isConnected);
        return result;

    }
}
