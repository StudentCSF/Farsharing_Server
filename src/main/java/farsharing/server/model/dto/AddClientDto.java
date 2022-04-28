package farsharing.server.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class AddClientDto {

    @NotBlank
    private UUID UserUid;

    @NotBlank
    private String license;

    @NotBlank
    private String firstName;

    private String midName;

    @NotBlank
    private String lastName;

    private String address;

    @NotBlank
    private String phoneNumber;

    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[12])/[0-9]{2}$")
    private String validThru;

    @Pattern(regexp = "^[1-9][0-9]{2}$")
    private String cvv;
}
