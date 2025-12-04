package jazi.gpshotel.repository;

import jazi.gpshotel.model.entity.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {

    List<Hotel> findHotelsByNameIgnoreCase(String name);

    List<Hotel> findHotelsByBrandIgnoreCase(String brand);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.address.city) = LOWER(:city)")
    List<Hotel> findHotelsByCity(String city);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.address.country) = LOWER(:country)")
    List<Hotel> findHotelsByCountry(String country);

    List<Hotel> findHotelsByAmenities(String amenities);

    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();

    @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city")
    List<Object[]> countHotelsByCity();

    @Query("SELECT h.address.country, COUNT(h) FROM Hotel h GROUP BY h.address.country")
    List<Object[]> countHotelsByCountry();

    @Query("SELECT a, COUNT(h) FROM Hotel h JOIN h.amenities a GROUP BY a")
    List<Object[]> countHotelsByAmenity();
}
