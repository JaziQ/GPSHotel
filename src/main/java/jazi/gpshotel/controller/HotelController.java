package jazi.gpshotel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jazi.gpshotel.model.dto.HotelFullDto;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.service.HotelService;
import jazi.gpshotel.service.HotelStatisticsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        List<HotelShortDto> hotels = hotelService.getAllHotelsShort();
        return ResponseEntity.ok(hotels);
    }

    @Operation(summary = "Create new Hotel",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Hotel created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HotelFullDto.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            })
    @PostMapping("/hotels")
    public ResponseEntity<HotelFullDto> createHotel(@Valid @RequestBody HotelFullDto hotelFullDto) {
        HotelFullDto createdHotel = hotelService.createHotel(hotelFullDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @Operation(summary = "Get hotel by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hotel found",
                            content = @Content(schema = @Schema(implementation = HotelFullDto.class))),
                    @ApiResponse(responseCode = "404", description = "Hotel not found")
            })
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelFullDto> getHotelById(
            @Parameter(description = "Hotel id", example = "123")
            @PathVariable Long id) {
        HotelFullDto hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @Operation(summary = "Search hotels with filters. If search parameters are not passed, all hotels are returned.")
    @GetMapping("/search")
    public ResponseEntity<List<HotelShortDto>> searchHotels(
            @Parameter(description = "Name", example = "DoubleTree by Hilton Minsk")
            @RequestParam(required = false) String name,
            @Parameter(description = "Brand", example = "Hilton")
            @RequestParam(required = false) String brand,
            @Parameter(description = "City", example = "Minsk")
            @RequestParam(required = false) String city,
            @Parameter(description = "Country", example = "Belarus")
            @RequestParam(required = false) String country,
            @Parameter(description = "List of amenities", example = "[\"Free WiFi\", \"Free parking\"]",
                    schema = @Schema(type = "array", example = "[\"Free WiFi\", \"Free parking\"]"))
            @RequestParam(required = false) List<String> amenities) {

        List<HotelShortDto> results = hotelService.searchHotels(name, brand, city, country, amenities);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Add amenities list to hotel by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Amenities added successfully",
                            content = @Content(schema = @Schema(implementation = HotelFullDto.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "404", description = "Hotel not found")
            })
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelFullDto> addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        HotelFullDto hotelFullDto = hotelService.addAmenitiesToHotel(id, amenities);
        return ResponseEntity.ok(hotelFullDto);
    }

    @Operation(summary = "Get hotels count by parameter",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Histogram retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter")
            })
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Integer>> getHistogram(
            @Parameter(
                    description = "Count hotels by params",
                    example = "city",
                    schema = @Schema(allowableValues = {"brand", "city", "country", "amenities"}))
            @PathVariable String param) {
        Map<String, Integer> result = hotelStatisticsService.getHistogram(param);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Deleting an entity by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Hotel deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Hotel not found")
            })
    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }
}
