package jazi.gpshotel.controller;

import jakarta.persistence.EntityNotFoundException;
import jazi.gpshotel.model.dto.HotelFullDto;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.service.HotelService;
import jazi.gpshotel.service.HotelStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelController {

    private final HotelService hotelService;
    private final HotelStatisticsService hotelStatisticsService;

    public HotelController(HotelService hotelService, HotelStatisticsService hotelStatisticsService) {
        this.hotelService = hotelService;
        this.hotelStatisticsService = hotelStatisticsService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelShortDto>> getAllHotels() {
        try {
            List<HotelShortDto> hotels = hotelService.getAllHotelsShort();
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelFullDto> createHotel(@RequestBody HotelFullDto hotelFullDto) {
        try {
            HotelFullDto createdHotel = hotelService.createHotel(hotelFullDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelFullDto> getHotelById(@PathVariable Long id) {
        try {
            HotelFullDto hotel = hotelService.getHotelById(id);
            if (hotel == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(hotel);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelShortDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {

        try {
            if (name == null && brand == null && city == null && country == null && amenities == null) {
                return ResponseEntity.badRequest().build();
            }
            List<HotelShortDto> results = hotelService.searchHotels(name, brand, city, country, amenities);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.emptyList());
        }
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelFullDto> addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        try {
            HotelFullDto hotelFullDto = hotelService.addAmenitiesToHotel(id, amenities);
            if (hotelFullDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Integer>> getHistogram(@PathVariable String param) {
        try {
            Map<String, Integer> result = hotelStatisticsService.getHistogram(param);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.emptyMap());
        }
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long id) {
        try {
            hotelService.deleteHotelById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}