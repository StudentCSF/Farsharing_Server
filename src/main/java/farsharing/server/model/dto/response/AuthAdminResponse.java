package farsharing.server.model.dto.response;

import farsharing.server.model.entity.ContractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Сущность с информацией для админа")
public class AuthAdminResponse {

    @Schema(description = "Cписок контрактов")
    private List<ContractEntity> contracts;
}
