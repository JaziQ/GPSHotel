package jazi.gpshotel.controller;

import jazi.gpshotel.exception.ResourceNotFoundException;
import jazi.gpshotel.model.dto.HotelFullDto;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.model.entity.Address;
import jazi.gpshotel.model.entity.ArrivalTime;
import jazi.gpshotel.model.entity.Contacts;
import jazi.gpshotel.service.HotelService;
import jazi.gpshotel.service.HotelStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private HotelStatisticsService hotelStatisticsService;

    @Autowired
    private ObjectMapper objectMapper;

    private HotelFullDto requestFullDto;
    private HotelFullDto responseFullDto;
    private HotelShortDto responseShortDto;

    @BeforeEach
    public void setup() {
        requestFullDto = createFullDto(null);
        responseFullDto = createFullDto(1);

        responseShortDto = createShortDto(1L);
    }

    @Test
    void getAllHotels_ShouldReturnHotels() throws Exception {
        when(hotelService.getAllHotelsShort()).thenReturn(List.of(responseShortDto));

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel name"));
    }

    @Test
    void getHotelById_ShouldReturnHotel() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(responseFullDto);

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hotel name"));
    }

    @Test
    void getHotelById_ShouldReturnCustom404() throws Exception {
        when(hotelService.getHotelById(999L))
                .thenThrow(new ResourceNotFoundException("Hotel with id 999 not found"));

        mockMvc.perform(get("/property-view/hotels/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createHotel_ShouldCreateAndReturnHotel() throws Exception {
        when(hotelService.createHotel(any(HotelFullDto.class))).thenReturn(responseFullDto);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestFullDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hotel name"))
                .andExpect(jsonPath("$.address.city").value("City"));
    }

    @Test
    void searchHotels_ShouldReturnResults() throws Exception {
        when(hotelService.searchHotels("Hotel name", null, null, null, null))
                .thenReturn(List.of(responseShortDto));

        mockMvc.perform(get("/property-view/search")
                        .param("name", "Hotel name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel name"));
    }

    @Test
    void deleteHotel_ShouldDeleteAndReturnNoContent() throws Exception {
        mockMvc.perform(delete("/property-view/hotels/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addAmenities_ShouldAddAmenitiesToHotel() throws Exception {
        responseFullDto.setAmenities(List.of("Free WiFi", "Free Parking", "Non-smoking rooms",
                "Fitness center", "Pet-friendly rooms"));

        when(hotelService.addAmenitiesToHotel(1L, List.of("Fitness center", "Pet-friendly rooms")))
                .thenReturn(responseFullDto);

        mockMvc.perform(post("/property-view/hotels/1/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"Fitness center\", \"Pet-friendly rooms\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getHistogram_ShouldReturnStatistics() throws Exception {
        Map<String, Integer> histogram = Map.of("Minsk", 10, "Grodno", 5);
        when(hotelStatisticsService.getHistogram("city")).thenReturn(histogram);

        mockMvc.perform(get("/property-view/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(10));
    }

    private HotelFullDto createFullDto(Integer id) {
        HotelFullDto dto = new HotelFullDto();

        if (id != null) {
            dto.setId(Long.valueOf(id));
        }

        dto.setName("Hotel name");
        dto.setBrand("Hotel Brand");
        dto.setDescription("Hotel Description");

        Contacts contacts = new Contacts();
        contacts.setEmail("hotel@gmail.com");
        contacts.setPhone("+375291234567");
        dto.setContacts(contacts);

        Address address = new Address();
        address.setCity("City");
        address.setCountry("Country");
        address.setStreet("Street");
        address.setHouseNumber(9);
        address.setPostCode("12345");
        dto.setAddress(address);

        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("14:00");
        arrivalTime.setCheckOut("12:00");
        dto.setArrivalTime(arrivalTime);

        dto.setAmenities(List.of("Free WiFi", "Free Parking", "Non-smoking rooms"));
        return dto;
    }

    private HotelShortDto createShortDto(Long id) {

        HotelShortDto shortDto = new HotelShortDto();
        shortDto.setId(id);
        shortDto.setName("Hotel name");
        shortDto.setPhone("+375291234567");
        return shortDto;
    }
}