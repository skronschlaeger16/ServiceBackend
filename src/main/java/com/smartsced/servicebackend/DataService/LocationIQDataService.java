package com.smartsced.servicebackend.DataService;

import com.smartsced.servicebackend.Dtos.LocationRessource;
import com.smartsced.servicebackend.Dtos.LongitudeLatitude;
import com.smartsced.servicebackend.Exceptions.LocationDataServiceException;
import com.smartsced.servicebackend.Exceptions.LocationNotFoundException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocationIQDataService {
    private static final String LOCATION_IQ_URL = "https://us1.locationiq.com/v1/search.php?key={apiKey}&q={searchString}&format=json";

    private static final String API_KEY = "1b4acab8d121a8";

    private static final String LOCATION_IQ_URL_LONG_LAT = "https://us1.locationiq.com/v1/reverse.php?key={apiKey}&lat={LATITUDE}&lon={LONGITUDE}&format=json";

    public LongitudeLatitude getLongitudeLatitudeByAddress(String address) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("apiKey", API_KEY);
        vars.put("searchString", address);

        try {
            ResponseEntity<LocationRessource[]> response = restTemplate.getForEntity(LOCATION_IQ_URL, LocationRessource[].class, vars);
            LongitudeLatitude result = new LongitudeLatitude();
            LocationRessource[] locations = response.getBody();
            result.setLongitude(locations[0].getLon());
            result.setLatitude(locations[0].getLat());
            return result;
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == 400) {
                throw new LocationNotFoundException("This Location was not found!");
            } else {
                throw new LocationDataServiceException("This was a bad request of Location!");
            }
        }

    }

    public String getAddress(String longitude, String Latitude) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("apiKey", API_KEY);
        vars.put("LONGITUDE", longitude);
        vars.put("LATITUDE", Latitude);

        try {
            ResponseEntity<LocationRessource> response = restTemplate.getForEntity(LOCATION_IQ_URL_LONG_LAT, LocationRessource.class, vars);
            String result = "";
            LocationRessource locations = response.getBody();
            result = locations.getDisplay_name();
            return result;
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == 400) {
                throw new LocationNotFoundException("This Location was not found!");
            } else {
                throw new LocationDataServiceException("This was a bad request of Location!");
            }
        }

    }
    }
