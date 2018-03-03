package com.penguinwan.domain;

/**
 * To check if two station is connected directly
 */
public interface IRouteFinder {
    /**
     * Check if two provided station is connected directly.
     * Since bus is usually going back and forth the same route, stations are considered connected as long as
     * they are in the same route regardless of their sequence.
     *
     * @param departure departure station
     * @param arrival   arrival station
     * @return true=is connected; false=not connected
     */
    boolean isConnected(Station departure, Station arrival);
}
