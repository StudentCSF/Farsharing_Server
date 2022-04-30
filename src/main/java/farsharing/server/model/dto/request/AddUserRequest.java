package farsharing.server.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AddUserRequest {

    @Pattern(regexp = "^.+@.+$")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}
