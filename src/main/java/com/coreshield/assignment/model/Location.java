package com.coreshield.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String id;
    private double latitude;
    private double longitude;
    private String type;
    private Double rating;
    private Integer reviewCount;

    public boolean isValid() {
        return type != null && rating != null && reviewCount != null;
    }
}
