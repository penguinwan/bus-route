package com.penguinwan.infrastructure.web;

import com.penguinwan.domain.IDataProvider;
import com.penguinwan.domain.IRouteFinder;
import com.penguinwan.domain.KeyValuePairRouteFinder;
import com.penguinwan.domain.SimpleRouteFinder;
import com.penguinwan.infrastructure.FileBasedDataProvider;
import com.penguinwan.infrastructure.InvalidFileException;
import com.penguinwan.infrastructure.InvalidFormatException;
import com.penguinwan.infrastructure.StreamingDataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {

    @Value("${data.file}")
    private String dataFileLocation;

    //@Bean
    public IRouteFinder keyValuePairRouteFinder(IDataProvider dataProvider) {
        return new KeyValuePairRouteFinder(dataProvider);
    }

    //@Bean
    public IDataProvider streamingDataProvider()
            throws InvalidFormatException, InvalidFileException {
        Path dataFilePath = Paths.get(dataFileLocation);
        return new StreamingDataProvider(dataFilePath);
    }

    @Bean
    public IRouteFinder simpleRouteFinder(IDataProvider dataProvider) {
        return new SimpleRouteFinder(dataProvider);
    }


    @Bean
    public IDataProvider fileBasedDataProvider()
            throws InvalidFormatException, InvalidFileException {
        Path dataFilePath = Paths.get(dataFileLocation);
        return new FileBasedDataProvider(dataFilePath);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
