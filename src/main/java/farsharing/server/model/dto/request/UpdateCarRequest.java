package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Schema(description = "Сущность для обновления данных об автомобиле")
public class UpdateCarRequest {

    @NotBlank
    @Schema(description = "Новое название бренда")
    private String brand;

    @NotBlank
    @Schema(description = "Новое название модели")
    private String model;

    @NotBlank
    @Schema(description = "Новое название цвета")
    private String color;

    @NotBlank
    @Schema(description = "Новое название типа кузова")
    private String bodyType;

    @NotNull
    @Schema(description = "Идентификатор нового местоположения")
    private UUID location;

    @NotNull
    @Min(1)
    @Schema(description = "Новая цена проката в час")
    private Float pricePerHour;

    @NotNull
    @Schema(description = "Актуальная информация о доступности")
    private Boolean isAvailable;

    @NotNull
    @Schema(description = "Новое значение километража")
    private Float mileage;
}
