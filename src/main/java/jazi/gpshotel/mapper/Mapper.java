package jazi.gpshotel.mapper;

import jazi.gpshotel.model.dto.HotelFullDto;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.model.entity.Address;
import jazi.gpshotel.model.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class Mapper {

    public Hotel toEntity(HotelFullDto dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setBrand(dto.getBrand());
        hotel.setAddress(dto.getAddress());
        hotel.setContacts(dto.getContacts());
        hotel.setArrivalTime(dto.getArrivalTime());
        hotel.setAmenities(Optional.ofNullable(dto.getAmenities())
                .orElse(new ArrayList<>()));
        return hotel;
    }

    public HotelFullDto toFullDto(Hotel hotel) {
        HotelFullDto dto = new HotelFullDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setBrand(hotel.getBrand());
        dto.setAddress(hotel.getAddress());
        dto.setContacts(hotel.getContacts());
        dto.setArrivalTime(hotel.getArrivalTime());
        dto.setAmenities(hotel.getAmenities());
        return dto;
    }

    public HotelShortDto toShortDto(Hotel hotel) {
        HotelShortDto dto = new HotelShortDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());

        if (hotel.getAddress() != null) {
            dto.setAddress(formatAddress(hotel.getAddress()));
        }

        if (hotel.getContacts() != null) {
            dto.setPhone(hotel.getContacts().getPhone());
        }

        return dto;
    }

    private String formatAddress(Address address) {
        if (address != null) {
            return address.getHouseNumber() + " " + address.getStreet() + ", " +
                    address.getCity() + ", " + address.getPostCode() + ", " + address.getCountry();
        } else {
            return null;
        }
    }
}