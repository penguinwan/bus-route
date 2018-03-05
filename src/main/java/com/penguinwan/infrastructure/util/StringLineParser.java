package com.penguinwan.infrastructure.util;

import com.penguinwan.domain.Route;
import com.penguinwan.domain.Station;
import com.penguinwan.infrastructure.InvalidFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Parser to parse data file line.
 * Format of data file line is : {numeric route id} {numeric station id} {numeric station id} {more numeric station id}
 * Example:
 * 101 56 47 20 78
 */
public final class StringLineParser {
    public static final String SEPARATOR = " ";

    /**
     * Nobody can instantiate this class.
     */
    private StringLineParser() {
    }

    /**
     * Parse the data file line to become Route.
     *
     * @param line
     * @return
     * @throws InvalidFormatException when the line is invalid, eg: non-numeric id is found
     */
    public static Route parse(String line) throws InvalidFormatException {
        List<Station> stations = new ArrayList<>();
        String[] tokens = line.trim().split(SEPARATOR);

        if (tokens.length < 2) {
            throw new InvalidFormatException(
                    String.format(
                            "Expected: Route id and followed by at least one station id; Result: [%s]",
                            line));
        }

        Optional<String> nonNumeric = Arrays.stream(tokens).filter(NumericChecker::isNonNumeric).findFirst();
        if (nonNumeric.isPresent()) {
            throw new InvalidFormatException(
                    String.format(
                            "Expected: Route id and station id must be integer; Result: [%s]",
                            nonNumeric.get()));
        }

        for (int i = 0; i < tokens.length; i++) {
            if (i == 0) continue;
            Station station = Station.of(Integer.parseInt(tokens[i]));
            stations.add(station);
        }
        return new Route(Integer.parseInt(tokens[0]), stations);
    }
}
