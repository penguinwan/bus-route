package com.penguinwan.domain;

public interface IRouteFinder {
    boolean isConnected(Station departure, Station arrival);
}
