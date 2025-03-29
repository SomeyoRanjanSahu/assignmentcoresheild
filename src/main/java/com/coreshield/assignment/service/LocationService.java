package com.coreshield.assignment.service;


import com.coreshield.assignment.model.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final List<Location> locations;

    public LocationService() throws IOException {
        this.locations = loadAndMergeData();
    }

    private List<Location> loadAndMergeData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Load JSON files
        List<Map<String, Object>> locationsData = mapper.readValue(new File("src/main/resources/static/locations.json"),
                new TypeReference<List<Map<String, Object>>>() {
                });
        List<Map<String, Object>> metadataData = mapper.readValue(new File("src/main/resources/static/metadata.json"),
                new TypeReference<List<Map<String, Object>>>() {
                });

        // Convert metadata list to a map for easy lookup
        Map<String, Map<String, Object>> metadataMap = metadataData.stream()
                .collect(Collectors.toMap(m -> (String) m.get("id"), Function.identity()));

        // Merge data
        return locationsData.stream()
                .map(loc -> {
                    String id = (String) loc.get("id");
                    double latitude = (double) loc.get("latitude");
                    double longitude = (double) loc.get("longitude");

                    Map<String, Object> meta = metadataMap.get(id);
                    if (meta != null) {
                        String type = (String) meta.get("type");
                        Double rating = meta.get("rating") != null ? ((Number) meta.get("rating")).doubleValue() : null;
                        Integer reviewCount = meta.get("reviews") != null ? ((Number) meta.get("reviews")).intValue() : null;
                        return new Location(id, latitude, longitude, type, rating, reviewCount);
                    }
                    return new Location(id, latitude, longitude, null, null, null); // Incomplete data case
                })
                .collect(Collectors.toList());
    }

    // Count valid locations per type
    public Map<String, Long> countValidLocationsPerType() {
        return locations.stream()
                .filter(Location::isValid)
                .collect(Collectors.groupingBy(Location::getType, Collectors.counting()));
    }

    // Calculate average rating per type
    public Map<String, Double> averageRatingPerType() {
        return locations.stream()
                .filter(Location::isValid)
                .collect(Collectors.groupingBy(Location::getType, Collectors.averagingDouble(Location::getRating)));
    }

    // Find location with the highest number of reviews
    public Optional<Location> highestReviewedLocation() {
        return locations.stream()
                .filter(Location::isValid)
                .max(Comparator.comparingInt(Location::getReviewCount));
    }

    // Identify locations with incomplete data
    public List<Location> getIncompleteLocations() {
        return locations.stream()
                .filter(loc -> !loc.isValid())
                .collect(Collectors.toList());
    }
}
