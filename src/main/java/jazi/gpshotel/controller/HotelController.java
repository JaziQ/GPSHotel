package jazi.gpshotel.controller;

import jakarta.persistence.EntityNotFoundException;
import jazi.gpshotel.model.dto.HotelFullDto;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
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

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelFullDto> addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        try{
            HotelFullDto hotelFullDto = hotelService.addAmenitiesToHotel(id, amenities);
            if (hotelFullDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
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