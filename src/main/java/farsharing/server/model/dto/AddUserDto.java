package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AddUserDto {

    @Pattern(regexp = "^.+@.+$")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}
