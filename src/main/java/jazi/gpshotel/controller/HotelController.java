package jazi.gpshotel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Hotel Controller")
public class HotelController {

    private final HotelService hotelService;
    private final HotelStatisticsService hotelStatisticsService;

    public HotelController(HotelService hotelService, HotelStatisticsService hotelStatisticsService) {
        this.hotelService = hotelService;
        this.hotelStatisticsService = hotelStatisticsService;
    }

    @Operation(summary = "Get all hotels in a short answer (name, description, address, phone number) ")
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelShortDto>> getAllHotels() {
        try {
            List<HotelShortDto> hotels = hotelService.getAllHotelsShort();
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Create new Hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hotel created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelFullDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")})
    @PostMapping("/hotels")
    public ResponseEntity<HotelFullDto> createHotel(@RequestBody HotelFullDto hotelFullDto) {
        try {
            HotelFullDto createdHotel = hotelService.createHotel(hotelFullDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get hotel by id")
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelFullDto> getHotelById(
            @Parameter(description = "Hotel id", example = "123")
            @PathVariable Long id) {
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

    @Operation(summary = "Search hotels with filters")
    @GetMapping("/search")
    public ResponseEntity<List<HotelShortDto>> searchHotels(
            @Parameter(description = "Name", example = "DoubleTree by Hilton Minsk")
            @RequestParam(required = false) String name,

            @Parameter(description = "brand", example = "Hilton")
            @RequestParam(required = false) String brand,

            @Parameter(description = "City", example = "Minsk")
            @RequestParam(required = false) String city,

            @Parameter(description = "Country", example = "Belarus")
            @RequestParam(required = false) String country,

            @Parameter(description = "List of amenities", example = "[\"Free WiFi\", \"Free parking\"]",
                    schema = @Schema(type = "array", example = "[\"Free WiFi\", \"Free parking\"]"))
            @RequestParam(required = false) List<String> amenities) {

        try {
            List<HotelShortDto> results = hotelService.searchHotels(name, brand, city, country, amenities);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.emptyList());
        }
    }

    @Operation(summary = "Add amenities list to hotel by id")
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelFullDto> addAmenities(
            @PathVariable Long id,
            @Parameter(description = "List of amenities", example = "[\"Free WiFi\", \"Free parking\"]",
                    schema = @Schema(type = "array", example = "[\"Free WiFi\", \"Free parking\"]"))
            @RequestBody List<String> amenities) {
        try {
            HotelFullDto hotelFullDto = hotelService.addAmenitiesToHotel(id, amenities);
            if (hotelFullDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(hotelFullDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get hotels count by parameter")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content( mediaType = "application/json",
                    schema = @Schema(implementation = Map.class, example = "{\"Minsk\": 5, \"Moscow\": 3}")))
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Integer>> getHistogram(
            @Parameter(
                    description = "Count hotels by params",
                    example = "city",
                    schema = @Schema(allowableValues = {"brand", "city", "country", "amenities"})
            )
            @PathVariable String param) {
        try {
            Map<String, Integer> result = hotelStatisticsService.getHistogram(param);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.emptyMap());
        }
    }

    @Operation(summary = "Deleting an entity by id")
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