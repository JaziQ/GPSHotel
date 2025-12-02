package jazi.gpshotel.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Embeddable
@Data
public class Contacts {

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Invalid phone number format")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;
}
