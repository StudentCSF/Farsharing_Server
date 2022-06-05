package farsharing.server.model.dto.response;

import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.ContractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Сущность с информацией о заявке клиента")
public class RequestInfoResponse {

    private ClientEntity client;

    private ContractEntity contract;
}
