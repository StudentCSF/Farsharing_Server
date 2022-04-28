package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddColorDto {

    @NotBlank
    private String name;

    @NotBlank
    private String hex;
}
