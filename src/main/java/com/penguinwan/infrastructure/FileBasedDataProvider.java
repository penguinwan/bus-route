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
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IDataProvider that load data from file.
 * NOTE: The loaded data will remain in memory.
 */
public class FileBasedDataProvider implements IDataProvider {
    private List<Route> routes = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param filePath path to the data file
     * @throws InvalidFileException   when the file is invalid, eg: file not found, error reading file
     * @throws InvalidFormatException when the file content is invalid, eg: non-numeric is found.
     */
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
                Route route = StringLineParser.parse(line);
                routes.add(route);
            }
        } catch (IOException ex) {
            throw new InvalidFileException("Error reading data file. ", ex);
        }
    }

    @Override
    public Iterable<Route> iterator() {
        return routes;
    }


}
