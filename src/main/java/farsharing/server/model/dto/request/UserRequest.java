package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Сущность авторизации пользователя")
public class UserRequest {

    @Pattern(regexp = "^.+@.+$")
    @Schema(description = "Адрес электронной почты")
    private String email;

    @NotBlank
    @Schema(description = "Пароль")
    private String password;
}
