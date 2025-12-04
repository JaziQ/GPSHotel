package jazi.gpshotel.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jazi.gpshotel.mapper.Mapper;
import jazi.gpshotel.model.dto.HotelShortDto;
import jazi.gpshotel.model.dto.HotelFullDto;
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

    @Transactional
    public HotelFullDto createHotel(@Valid HotelFullDto hotelFullDto) {
        Hotel hotel = mapper.toEntity(hotelFullDto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return mapper.toFullDto(savedHotel);
    }

    public List<HotelShortDto> getAllHotelsShort() {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        return StreamSupport.stream(hotels.spliterator(), false)
                .map(mapper::toShortDto)
                .toList();
    }

    public HotelFullDto getHotelById(Long id) {
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
    public Optional<HotelFullDto> addAmenitiesToHotel(Long id, List<String> amenities) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.getAmenities().addAll(amenities);
                    hotelRepository.save(hotel);
                    return mapper.toFullDto(hotel);
                });
    }

}
