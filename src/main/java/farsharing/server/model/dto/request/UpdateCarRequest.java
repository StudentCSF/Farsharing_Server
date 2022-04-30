package farsharing.server.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UpdateCarRequest {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @NotBlank
    private String bodyType;

    private UUID location;

    @NotNull
    @Min(1)
    private Float pricePerHour;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private Float mileage;
}
