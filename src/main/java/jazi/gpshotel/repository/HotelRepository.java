package jazi.gpshotel.repository;

import jazi.gpshotel.model.entity.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {

    @Query("""
            SELECT h FROM Hotel h WHERE
            (:name IS NULL OR LOWER(h.name) = LOWER(:name))
            AND (:brand IS NULL OR LOWER(h.brand) = LOWER(:brand))
            AND (:city IS NULL OR LOWER(h.address.city) = LOWER(:city))
            AND (:country IS NULL OR LOWER(h.address.country) = LOWER(:country))
            AND (:amenities IS NULL OR 
                        :amenitiesCount = (SELECT COUNT(DISTINCT a) FROM h.amenities a WHERE a IN :amenities))
            """)
    List<Hotel> findHotelsByParams(@Param("name") String name, @Param("brand") String brand, @Param("city") String city,
                                   @Param("country") String country, @Param("amenities") List<String> amenities,
                                   @Param("amenitiesCount") Integer amenitiesCount);

    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();

    @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city")
    List<Object[]> countHotelsByCity();

    @Query("SELECT h.address.country, COUNT(h) FROM Hotel h GROUP BY h.address.country")
    List<Object[]> countHotelsByCountry();

    @Query("SELECT a, COUNT(h) FROM Hotel h JOIN h.amenities a GROUP BY a")
    List<Object[]> countHotelsByAmenity();
}
