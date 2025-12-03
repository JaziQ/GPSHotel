package jazi.gpshotel.mapper;

import jazi.gpshotel.model.dto.HotelDto;
import jazi.gpshotel.model.entity.Address;
import jazi.gpshotel.model.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class Mapper {

    public Hotel toEntity(HotelDto dto) {
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

    public HotelDto toFullDto(Hotel hotel) {
        return HotelDto.createFullDto(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getBrand(),
                hotel.getAddress(), hotel.getContacts(), hotel.getArrivalTime(), hotel.getAmenities());
    }

    public HotelDto toShortDto(Hotel hotel) {
        String formattedAddress = formatAddress(hotel.getAddress());
        String phone = hotel.getContacts().getPhone();

        return HotelDto.createShortDto(hotel.getId(), hotel.getName(),
                hotel.getDescription(), formattedAddress, phone);
    }

    private String formatAddress(Address address) {
        return address.getHouseNumber() + " " + address.getStreet() + ", " +
                address.getCity() + ", " + address.getPostalCode() + ", " + address.getCountry();
    }
}
