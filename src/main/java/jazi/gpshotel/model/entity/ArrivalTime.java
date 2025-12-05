package jazi.gpshotel.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "Check-in and check-out times")
@Embeddable
@Data
public class ArrivalTime {

    @Schema(description = "Check-in time", example = "14:00", pattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Check-out time must be in format HH:MM (24-hour)")
    private String checkIn;

    @Schema(description = "Check-out time (optional)", example = "12:00", pattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Check-out time must be in format HH:MM (24-hour)")
    private String checkOut;
}
