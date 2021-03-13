package com.trip.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Suggestion {

    @JsonProperty("_id")
    private long id;
    private String key;
    private String name;
    private String fullName;
    @JsonProperty("iata_airport_code")
    private String iataAirportCode;
    private String type;
    private String country;
    @JsonProperty("geo_position")
    private GeoPosition geoPosition;

    private String  locationId;
    private boolean inEurope;
    private int countryId;
    private String countryCode;
    private boolean coreCountry;
    private long distance;
    private Map<String, String> names;
    private Map<String, String> alternativeNames;


}
