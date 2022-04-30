package farsharing.server.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "Cущность с краткой информацией об автомобиле")
public class BriefCarInfoResponse {

    @Schema(description = "Идентификатор - госномер")
    private String stateNumber;

    @Schema(description = "Название бренда")
    private String brand;

    @Schema(description = "Название модели")
    private String model;

    @Schema(description = "Сведения о возможности взять на прокат в данный момент")
    private Boolean isAvailable;
}
