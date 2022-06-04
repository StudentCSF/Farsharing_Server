package farsharing.server.model.dto.response;

import farsharing.server.model.entity.enumerate.ContractStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CarResponse {

    private Boolean isFree;

    private Boolean thisClient;

    private ContractStatus status;

    private UUID contractUid;
}
