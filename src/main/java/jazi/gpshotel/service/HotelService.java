package jazi.gpshotel.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jazi.gpshotel.mapper.Mapper;
import jazi.gpshotel.model.dto.HotelDto;
import jazi.gpshotel.model.entity.Hotel;
import jazi.gpshotel.repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final Mapper mapper;

    public HotelService(HotelRepository hotelRepository, Mapper mapper) {
        this.hotelRepository = hotelRepository;
        this.mapper = mapper;
    }

    public Hotel createHotel(@Valid HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setBrand(hotelDto.getBrand());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setContacts(hotelDto.getContacts());
        hotel.setArrivalTime(hotelDto.getArrivalTime());

        return hotelRepository.save(hotel);
    }

    public List<HotelDto> getAllHotelsShort() {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        return StreamSupport.stream(hotels.spliterator(), false)
                .map(mapper::toShortDto)
                .toList();
    }

    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id " + id + " not found"));
        return mapper.toFullDto(hotel);
    }

    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id " + id + " not found"));
        hotelRepository.deleteById(hotel.getId());
    }

    @Transactional
    public Optional<HotelDto> addAmenitiesToHotel(Long id, List<String> amenities) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.getAmenities().addAll(amenities);
                    hotelRepository.save(hotel);
                    return mapper.toFullDto(hotel);
                });
    }

}
