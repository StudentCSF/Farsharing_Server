package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {

    @Pattern(regexp = "^.+@.+$")
    private String email;

    @NotBlank
    private String password;
}
