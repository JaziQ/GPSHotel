package jazi.gpshotel.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Embeddable
@Data
@Schema(description = "Address information")
public class Address {

    @Schema(description = "House number", example = "9", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Street is mandatory")
    @Positive(message = "House number must be positive")
    private Integer houseNumber;

    @Schema(description = "Street name", example = "Pobediteley Avenue", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Street is mandatory")
    private String street;

    @Schema(description = "City", example = "Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "City is mandatory")
    private String city;

    @Schema(description = "Country", example = "Belarus", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Country is mandatory")
    private String country;

    @Schema(description = "Postal code", example = "220004", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Postal Code is mandatory")
    private String postCode;

}
