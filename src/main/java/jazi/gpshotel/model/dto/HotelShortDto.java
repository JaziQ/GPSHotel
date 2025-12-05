package jazi.gpshotel.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Short hotel information")
@Data
public class HotelShortDto {

    @Schema(description = "Unique hotel identifier", example = "123")
    @NotNull
    private Long id;

    @Schema(description = "Hotel name", example = "Grand Hotel Moscow")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "Hotel description", example = "The DoubleTree by Hilton Hotel Minsk " +
            "offers 193 luxurious rooms in the Belorussian capital...")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Schema(description = "Full address", example = "9 Pobediteley Avenue, Minsk, 220004, Belarus")
    @NotNull(message = "Address is mandatory")
    private String address;

    @Schema(description = "Phone number", example = "+375 17 309-80-00")
    @NotNull(message = "Phone number is mandatory")
    private String phone;

}