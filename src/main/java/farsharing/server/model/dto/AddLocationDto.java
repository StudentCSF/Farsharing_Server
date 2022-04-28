package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddLocationDto {

    @NotBlank
    private String country;

    @NotBlank
    private String city;
}
