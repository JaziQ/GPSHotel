package jazi.gpshotel.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Embeddable
@Data
public class ArrivalTime {

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Check-out time must be in format HH:MM (24-hour)")
    private Date checkIn;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Check-out time must be in format HH:MM (24-hour)")
    private String checkOut;
}
