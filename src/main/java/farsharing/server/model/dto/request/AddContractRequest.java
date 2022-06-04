package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Schema(description = "Сущность для добавления контрактов/броней")
public class AddContractRequest {

    @NotNull
    @Schema(description = "Идентификатор клиента")
    private UUID clientUid;

    @NotNull
    @Schema(description = "Идентификатор машины")
    private UUID carUid;

    @NotNull
    @Schema(description = "Начало проката")
    private ZonedDateTime startTime;

    @NotNull
    @Schema(description = "Конец проката")
    private ZonedDateTime endTime;
}
