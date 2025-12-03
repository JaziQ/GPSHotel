package jazi.gpshotel.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jazi.gpshotel.model.entity.Address;
import jazi.gpshotel.model.entity.ArrivalTime;
import jazi.gpshotel.model.entity.Contacts;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Size(max = 100, message = "Brand name cannot exceed 100 characters")
    private String brand;

    @NotNull(message = "Address is mandatory")
    @Valid
    private Address address;

    @NotNull(message = "Contacts are mandatory")
    @Valid
    private Contacts contacts;

    @Valid
    private ArrivalTime arrivalTime;

    private String formattedAddress;
    private String phone;

    private List<String> amenities;

    public static HotelDto createShortDto(Long id, String name, String description, String formattedAddress, String phone) {
        HotelDto hotelDto = new HotelDto();

        hotelDto.setId(id);
        hotelDto.setName(name);
        hotelDto.setDescription(description);
        hotelDto.setFormattedAddress(formattedAddress);
        hotelDto.setPhone(phone);
        return hotelDto;
    }

    public static HotelDto createFullDto(Long id, String name, String description,
                                         String brand, Address address, Contacts contacts,
                                         ArrivalTime arrivalTime, List<String> amenities) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(id);
        hotelDto.setName(name);
        hotelDto.setDescription(description);
        hotelDto.setBrand(brand);
        hotelDto.setAddress(address);
        hotelDto.setContacts(contacts);
        hotelDto.setArrivalTime(arrivalTime);
        hotelDto.setAmenities(amenities);
        return hotelDto;
    }
}