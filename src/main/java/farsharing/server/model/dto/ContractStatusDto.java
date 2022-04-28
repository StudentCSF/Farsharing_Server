package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ContractStatusDto {

    @NotBlank
    private String contractStatus;
}
