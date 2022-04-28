package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AddColorDto {

    @NotBlank
    private String name;

    @Pattern(regexp = "^[0-9A-Fa-f]{6}$")
    private String hex;
}
