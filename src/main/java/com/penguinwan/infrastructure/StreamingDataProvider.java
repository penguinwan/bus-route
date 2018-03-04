package com.penguinwan.infrastructure;

import com.penguinwan.domain.IDataProvider;
import com.penguinwan.domain.Route;
import com.penguinwan.infrastructure.util.NumericChecker;
import com.penguinwan.infrastructure.util.StringLineParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Streaming data provider will stream data file and return routes data.
 * Streaming data provider will not store route data in its class.
 * NOTE: Every invocation of iterator() will initiate the streaming again.
 */
public class StreamingDataProvider implements IDataProvider {
    private Path dataFilePath;

    /**
     * Constructor.
     *
     * @param filePath path of the data file
     * @throws InvalidFileException   when the file is invalid, eg: file not found, error reading file
     * @throws InvalidFormatException when the file content is invalid, eg: non-numeric is found.
     */
    public StreamingDataProvider(Path filePath) throws InvalidFileException, InvalidFormatException {
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

            // drill, to check if there is any non-numeric in file
            while ((line = reader.readLine()) != null) {
                StringLineParser.parse(line);
            }


        } catch (IOException ex) {
            throw new InvalidFileException("Error reading data file. ");
        }
        this.dataFilePath = filePath;
    }

    @Override
    public Iterable<Route> iterator() {
        return () -> new RouteIterator(dataFilePath);
    }

    class RouteIterator implements Iterator<Route> {
        private Scanner scanner;
        private boolean hasErrorInitiateReader = false;

        private RouteIterator(Path dataFile) {
            try {
                scanner = new Scanner(Files.newBufferedReader(dataFile));
                String numberOfRoutes = scanner.nextLine();
            } catch (IOException ex) {
                //TODO logging
                hasErrorInitiateReader = true;
            }
        }

        @Override
        public boolean hasNext() {
            if (hasErrorInitiateReader) return false;

            boolean hasNext = scanner.hasNext();
            if (!hasNext) {
                scanner.close();
            }
            return hasNext;
        }

        @Override
        public Route next() {
            try {
                return StringLineParser.parse(scanner.nextLine());
            } catch (InvalidFormatException ex) {
                // would not reach here because constructor has already made sure all data is correct
                throw new RuntimeException("Ops...programming error, constructor did not do its job.");
            }
        }
    }
}
