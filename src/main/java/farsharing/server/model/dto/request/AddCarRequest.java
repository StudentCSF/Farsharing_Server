package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Schema(description = "Сущность машины для добавления в БД")
public class AddCarRequest {

    @NotBlank
    @Schema(description = "Название бренда")
    private String brand;

    @NotBlank
    @Schema(description = "Название модели")
    private String model;

    @NotBlank
    @Schema(description = "Название цвета")
    private String color;

    @NotBlank
    @Schema(description = "Название типа кузова")
    private String bodyType;

    @NotNull
    @Schema(description = "Идентификатор местоположения")
    private UUID location;

    @NotBlank
    @Schema(description = "Госномер")
    private String stateNumber;

    @NotNull
    @Min(1)
    @Schema(description = "Цена проката в час")
    private Float pricePerHour;

    @NotNull
    @Schema(description = "Километраж")
    private Float mileage;
}
