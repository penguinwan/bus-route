package com.penguinwan.domain;

/**
 * To provide route data in iterable way.
 */
public interface IDataProvider {
    /**
     * Provides routes iterator.
     * Would NOT return null if there is no data.
     *
     * @return routes' iterator
     */
    Iterable<Route> iterator();
}
