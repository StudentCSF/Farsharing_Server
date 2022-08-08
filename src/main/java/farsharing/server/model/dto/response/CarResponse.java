package farsharing.server.model.dto.response;

import farsharing.server.model.entity.enumerate.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "Сущность с дополненной информацией об автомобиле")
public class CarResponse {

    @Schema(description = "Свободен ли автомобиль")
    private Boolean isFree;

    @Schema(description = "Этот клиент отправлял заявку на бронь?")
    private Boolean thisClient;

    @Schema(description = "Статус контракта")
    private ContractStatus status;

    @Schema(description = "Идентификатор контракта")
    private UUID contractUid;
}
