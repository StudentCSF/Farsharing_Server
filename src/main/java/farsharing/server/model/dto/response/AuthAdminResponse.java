package farsharing.server.model.dto.response;

import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Сущность с информацией для админа")
public class AuthAdminResponse {

//    @Schema(description = "Идентификатор администратора")
//    private UUID uid;

    @Schema(description = "Cписок клиентов")
    private List<ClientEntity> clients;
}
