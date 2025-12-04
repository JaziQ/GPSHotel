package jazi.gpshotel.model.dto;

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
public class HotelFullDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Size(max = 100, message = "Brand name cannot exceed 100 characters")
    private String brand;

    @NotNull(message = "Address is mandatory")
    @Valid
    private Address address;

    @NotNull(message = "Contacts are mandatory")
    @Valid
    private Contacts contacts;

    @Valid
    private ArrivalTime arrivalTime;

    private List<String> amenities;

}