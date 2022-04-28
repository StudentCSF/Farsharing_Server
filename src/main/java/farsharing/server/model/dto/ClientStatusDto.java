package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientStatusDto {

    @NotBlank
    private String clientStatus;
}
