package com.coreshield.assignment.controllers;

import com.coreshield.assignment.model.Location;
import com.coreshield.assignment.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/count-per-type")
    public Map<String, Long> getValidLocationCounts() {
        return locationService.countValidLocationsPerType();
    }

    @GetMapping("/average-rating")
    public Map<String, Double> getAverageRatings() {
        return locationService.averageRatingPerType();
    }

    @GetMapping("/highest-reviewed")
    public Optional<Location> getHighestReviewedLocation() {
        return locationService.highestReviewedLocation();
    }

    @GetMapping("/incomplete")
    public List<Location> getIncompleteLocations() {
        return locationService.getIncompleteLocations();
    }
}
