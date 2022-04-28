package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddCarDto {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @NotBlank
    private String bodyType;

    private UUID location;

    @NotBlank
    private String stateNumber;

    @NotNull
    @Min(1)
    private Float pricePerHour;

    @NotNull
    private Float mileage;
}
