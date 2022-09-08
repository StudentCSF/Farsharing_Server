package farsharing.server.model.dto.response;

import farsharing.server.model.entity.CarEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Data
@Builder
@Schema(description = "Cущность с информацией для клиента")
public class AuthClientPageableResponse {

    @Schema(description = "Идентификатор клиента (не пользователся/юзера!)")
    private UUID clientUid;

    @Schema(description = "Cписок машин")
    private Page<CarEntity> cars;
}
