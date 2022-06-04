package farsharing.server.model.dto.response;

import farsharing.server.model.entity.CarEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AuthClientResponse implements IAuthResponse {
    private UUID uid;
    private List<CarEntity> cars;
}
