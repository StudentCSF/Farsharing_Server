package farsharing.server.model.dto.response;

import farsharing.server.model.entity.CarEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthClientResponse implements IAuthResponse {
    private List<CarEntity> cars;
}
