package jazi.gpshotel.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jazi.gpshotel.model.entity.Address;
import jazi.gpshotel.model.entity.ArrivalTime;
import jazi.gpshotel.model.entity.Contacts;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Full hotel information")
public class HotelFullDto {

    @Schema(description = "Unique hotel identifier", example = "123")
    @NotNull
    private Long id;

    @Schema(description = "Hotel name", example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "Hotel description", example = "The DoubleTree by Hilton Hotel Minsk " +
            "offers 193 luxurious rooms in the Belorussian capital...")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Schema(description = "Hotel brand", example = "Hilton")
    @Size(max = 100, message = "Brand name cannot exceed 100 characters")
    private String brand;

    @Schema(description = "Hotel address")
    @NotNull(message = "Address is mandatory")
    @Valid
    private Address address;

    @Schema(description = "Contact information")
    @NotNull(message = "Contacts are mandatory")
    @Valid
    private Contacts contacts;

    @Schema(description = "Check-in, check-out time", example = "14:00")
    @Valid
    private ArrivalTime arrivalTime;

    @Schema(description = "List of amenities", example = "[\"Free parking\", \"Non-smoking rooms\"",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> amenities;

}