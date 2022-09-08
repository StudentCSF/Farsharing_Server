package farsharing.server.model.dto.response;

import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
@Schema(description = "Сущность с информацией для админа")
public class AuthAdminPageableResponse {

    @Schema(description = "Cписок клиентов")
    private Page<ClientEntity> clients;
}
