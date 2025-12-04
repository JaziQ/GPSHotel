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
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{7,30}$",
            message = "Invalid phone number format")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;
}
