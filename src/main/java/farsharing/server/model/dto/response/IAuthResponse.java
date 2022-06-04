package farsharing.server.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Cущность с информацией для клиента или админа. Может быть NULL. Одно из полей обязательно NULL")
public class IAuthResponse {

    private AuthAdminResponse authAdminResponse;

    private AuthClientResponse authClientResponse;

}
