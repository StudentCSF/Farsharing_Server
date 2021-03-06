package farsharing.server.model.dto.response;

import farsharing.server.model.entity.CarEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Cущность с информацией для клиента")
public class AuthClientResponse {

    @Schema(description = "Идентификатор клиента")
    private UUID uid;

    @Schema(description = "Cписок машин")
    private List<CarEntity> cars;
}
