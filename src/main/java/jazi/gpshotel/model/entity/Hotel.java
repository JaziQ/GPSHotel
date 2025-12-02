package jazi.gpshotel.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Entity
@Table
@Data
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Hotel name is mandatory")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Size(max = 100, message = "Brand name cannot exceed 100 characters")
    private String brand;

    @NotNull(message = "Address is mandatory")
    @Valid
    @Embedded
    private Address address;

    @Valid
    @Embedded
    private Contacts contacts;

    @Valid
    @Embedded
    private ArrivalTime arrivalTime;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;

}
