package jazi.gpshotel.service;

import jazi.gpshotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelStatisticsService {
    private final HotelRepository hotelRepository;

    public HotelStatisticsService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Map<String, Integer> getHistogram(String param) {
        if (param == null || param.isEmpty()) {
            return Collections.emptyMap();
        }
        if (param.equalsIgnoreCase("brand")) {
            return countByBrand();
        }
        if (param.equalsIgnoreCase("city")) {
            return countByCity();
        }
        if (param.equalsIgnoreCase("country")) {
            return countByCountry();
        }
        if (param.equalsIgnoreCase("amenities")) {
            return countByAmenities();
        }
        return Collections.emptyMap();
    }


    private Map<String, Integer> countByBrand() {
        List<Object[]> countedBrand = hotelRepository.countHotelsByBrand();
        return convertToMap(countedBrand);
    }


    private Map<String, Integer> countByCity() {
        List<Object[]> countedCity = hotelRepository.countHotelsByCity();
        return convertToMap(countedCity);
    }

    private Map<String, Integer> countByCountry() {
        List<Object[]>  countedCountry = hotelRepository.countHotelsByCountry();
        return  convertToMap(countedCountry);
    }

    private Map<String, Integer> countByAmenities() {
        List<Object[]> countedAmenity = hotelRepository.countHotelsByAmenity();
        return  convertToMap(countedAmenity);
    }

    private Map<String, Integer> convertToMap(List<Object[]> response) {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Object[] row : response) {
            if (row[0] != null) {
                result.put(
                        row[0].toString(),
                        ((Number) row[1]).intValue()
                );
            }
        }
        return result;
    }
}
