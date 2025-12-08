package jazi.gpshotel.service;

import jazi.gpshotel.exception.ResourceNotFoundException;
import jazi.gpshotel.mapper.Mapper;
import jazi.gpshotel.model.dto.HotelFullDto;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.model.entity.Address;
import jazi.gpshotel.model.entity.ArrivalTime;
import jazi.gpshotel.model.entity.Contacts;
import jazi.gpshotel.model.entity.Hotel;
import jazi.gpshotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    private HotelService hotelService;

    private HotelFullDto requestHotelFullDto;
    private Hotel hotelId1;
    private Hotel hotelId2;


    @BeforeEach
    public void setup() {
        Mapper mapper = new Mapper();
        hotelService = new HotelService(hotelRepository, mapper);
        requestHotelFullDto = createHotelFullDto(null);

        hotelId1 = createHotelEntity(1L);
        hotelId2 = createHotelEntity(2L);
    }

    @Test
    void getHotelById_ShouldReturnHotelDto() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelId1));

        HotelFullDto result = hotelService.getHotelById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Hotel name");

    }

    @Test
    void getHotelById_ShouldThrowException() {
        when(hotelRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.getHotelById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Hotel with id 999 not found");
    }

    @Test
    void getAllHotelsShort_ShouldReturnListOfShortDto() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotelId1, hotelId2));

        List<HotelShortDto> result = hotelService.getAllHotelsShort();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);

    }

    @Test
    void createHotel_ShouldSaveAndReturnDto() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelId1);

        HotelFullDto result = hotelService.createHotel(requestHotelFullDto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Hotel name");

    }

    @Test
    void deleteHotelById_ShouldDelete() {
        when(hotelRepository.existsById(1L)).thenReturn(true);

        hotelService.deleteHotelById(1L);
    }

    @Test
    void deleteHotelById_ShouldThrow() {
        when(hotelRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> hotelService.deleteHotelById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Hotel with id 999 not found. Not deleted.");

    }

    @Test
    void addAmenitiesToHotel_ShouldAddNewAmenities() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelId1));

        HotelFullDto result = hotelService.addAmenitiesToHotel(1L, List.of("Free WiFi", "Fitness center"));

        assertThat(result.getAmenities())
                .containsExactly("Free WiFi", "Free Parking", "Fitness center");

    }

    @Test
    void addAmenities_ShouldNotAddDuplicates(){
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelId1));

        HotelFullDto result = hotelService.addAmenitiesToHotel(1L,
                List.of("Free WiFi", "Free Parking", "Fitness center"));

        assertThat(result.getAmenities())
                .containsExactly("Free WiFi", "Free Parking", "Fitness center");
    }

    @Test
    void addAmenitiesToHotel_ShouldThrow() {
        when(hotelRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.addAmenitiesToHotel(999L, List.of("Pool")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Hotel with id 999 not found");

    }

    @Test
    void searchHotels_ShouldReturnFilteredResults() {
        when(hotelRepository.findHotelsByParams("Hotel", null, "Minsk", null, null, 0))
                .thenReturn(List.of(hotelId1));

        List<HotelShortDto> result = hotelService.searchHotels("Hotel", null, "Minsk", null, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);

    }

    private HotelFullDto createHotelFullDto(Integer id) {
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

        List<String> amenities = List.of("Free WiFi", "Free Parking");
        dto.setAmenities(amenities);
        return dto;
    }

    private Hotel createHotelEntity(Long id) {
        Hotel hotel = new Hotel();

        if (id != null) {
            hotel.setId(id);
        }

        hotel.setName("Hotel name");
        hotel.setBrand("Hotel Brand");
        hotel.setDescription("Hotel Description");

        Contacts contacts = new Contacts();
        contacts.setEmail("hotel@gmail.com");
        contacts.setPhone("+375291234567");
        hotel.setContacts(contacts);

        Address address = new Address();
        address.setCity("City");
        address.setCountry("Country");
        address.setStreet("Street");
        address.setHouseNumber(9);
        address.setPostCode("12345");
        hotel.setAddress(address);

        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("14:00");
        arrivalTime.setCheckOut("12:00");
        hotel.setArrivalTime(arrivalTime);

        hotel.setAmenities(new java.util.ArrayList<>(List.of("Free WiFi", "Free Parking")));
        return hotel;
    }

}