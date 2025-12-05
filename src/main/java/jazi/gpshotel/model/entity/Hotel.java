package jazi.gpshotel.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Schema(description = "Hotel entity", hidden = true)
@Entity
@Table
@Data
public class Hotel {

    @Schema(description = "Unique identifier", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_seq")  // ← SEQUENCE!
    @SequenceGenerator(name = "hotel_seq", sequenceName = "HOTEL_SEQ", allocationSize = 1)
    private Long id;

    @Schema(description = "Hotel name", example = "Grand Hotel Moscow", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Hotel name is mandatory")
    private String name;

    @Schema(description = "Hotel description", example = "The DoubleTree by Hilton Hotel Minsk " +
            "offers 193 luxurious rooms in the Belorussian capital...")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Schema(description = "Hotel brand", example = "Hilton")
    @Size(max = 100, message = "Brand name cannot exceed 100 characters")
    private String brand;

    @Schema(description = "Hotel address", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Address is mandatory")
    @Valid
    @Embedded
    private Address address;

    @Schema(description = "Contact information", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @Embedded
    private Contacts contacts;

    @Schema(description = "Check-in and check-out times")
    @Valid
    @Embedded
    private ArrivalTime arrivalTime;

    @Schema(description = "List of amenities", example = "[\"Free parking\", \"Non-smoking rooms\"")
    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;

}
