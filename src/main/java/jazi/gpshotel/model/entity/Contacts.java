package jazi.gpshotel.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "Contact information")
@Embeddable
@Data
public class Contacts {

    @Schema(description = "Phone number", example = "+375 17 309-80-00")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{7,30}$",
            message = "Invalid phone number format")
    private String phone;

    @Schema(description = "Email address", example = "doubletreeminsk.info@hilton.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "Email should be valid")
    private String email;
}
