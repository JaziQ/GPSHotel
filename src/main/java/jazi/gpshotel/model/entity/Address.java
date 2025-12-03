package jazi.gpshotel.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Embeddable
@Data
public class Address {

    @NotNull(message = "Street is mandatory")
    @Positive(message = "House number must be positive")
    private Integer houseNumber;

    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @NotBlank(message = "Postal Code is mandatory")
    private String postalCode;

}
