package com.penguinwan.infrastructure;

import com.penguinwan.domain.IDataProvider;
import com.penguinwan.domain.Route;
import com.penguinwan.domain.Station;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of IDataProvider that load data from file.
 * NOTE: The loaded data will remain in memory.
 */
public class FileBasedDataProvider implements IDataProvider {
    private List<Route> routes = new ArrayList<>();

    public FileBasedDataProvider(Path filePath) throws InvalidFileException, InvalidFormatException {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }

        if (!Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
            throw new InvalidFileException("File path: " + filePath.toString());
        }

        try (
                BufferedReader reader = Files.newBufferedReader(filePath)
        ) {
            String line = reader.readLine();

            if (NumericChecker.isNonNumeric(line)) {
                throw new InvalidFormatException(
                        String.format(
                                "Expected: First line to be the number of routes; Result: [%s]",
                                line));
            }

            while ((line = reader.readLine()) != null) {
                Route route = LineParser.parse(line);
                routes.add(route);
            }
        } catch (IOException ex) {
            throw new InvalidFileException("Error reading data file. ");
        }
    }

    @Override
    public Iterable<Route> iterator() {
        return routes;
    }

    /**
     * Utility class that checks if a given string is numeric.
     */
    private static class NumericChecker {
        static boolean isNonNumeric(String s) {
            return isNonNumeric(s, 10);
        }

        private static boolean isNonNumeric(String s, int radix) {
            if (s == null || s.isEmpty()) return true;
            for (int i = 0; i < s.length(); i++) {
                if (i == 0 && s.charAt(i) == '-') {
                    if (s.length() == 1) return true;
                    else continue;
                }
                if (Character.digit(s.charAt(i), radix) < 0) return true;
            }
            return false;
        }
    }

    /**
     * To parse line in data file, and return Route.
     */
    private static class LineParser {
        static final String SEPARATOR = " ";

        static Route parse(String line) throws InvalidFormatException {
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
}
