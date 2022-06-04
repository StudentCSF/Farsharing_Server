package farsharing.server.model.dto.response;

import farsharing.server.model.entity.ContractEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthAdminResponse implements IAuthResponse {
    private List<ContractEntity> contracts;
}
