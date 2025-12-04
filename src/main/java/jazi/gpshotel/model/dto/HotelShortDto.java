package jazi.gpshotel.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HotelShortDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Phone number is mandatory")
    private String phone;

}