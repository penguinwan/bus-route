# Introduction
Find detailed explanation in [here](https://github.com/goeuro/challenges/tree/master/bus_route_challenge)

# How to
- sample data attached, run the spring boot program by `java -Ddata.file=data/sample-data -jar target/bus-route-1.0-SNAPSHOT.jar`
- request url by `http://localhost:8088/api/direct?dep_sid=2&arr_sid=5`

# Available combo set
- combo1 - `FileBasedDataProvider` + `SimpleRouteFinder` - This combination employs simple algorithm to find route result. This combination works good in small data set. Read individual class javadoc for detail.
- combo2 - `StreamingDataProvider` + `KeyValuePairRouteFinder` - This combination store station and route information in map, so as to quicken the search. This combination works good in large data set, however application startup time will be longer. Read individual class javadoc for detail.
- change the combo set in `Application.java`, default is combo1.

# Benchmark
- FileBasedDataProvider + SimpleRouteFinder
    - 100000 routes and each with 1000 stations, data file size is 390MB
        - program runs occupying 3.3GB 
        - first GET request is returned in 500ms
        - subsequent GET request is returned in 200ms
    - 3 routes and each with 3 stations, data file size is 1byte
        - program runs occupying 287MB
        - first GET request is returned in 200ms
        - subsequent GET request is returned in 30ms
- StreamingDataProvider + KeyValuePairRouteFinder
    - 100000 routes and each with 1000 stations, data file size is 390MB
        - program runs occupying 3.43GB 
        - first GET request is returned in 150ms
        - subsequent GET request is returned in 30ms
    - 3 routes and each with 3 stations, data file size is 1byte
        - program runs occupying 279MB
        - first GET request is returned in 200ms
        - subsequent GET request is returned in 20ms