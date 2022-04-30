package farsharing.server.model.dto.request;

import javax.validation.constraints.*;

import lombok.Data;

import java.util.UUID;

@Data
public class AddClientRequest {

    @NotNull
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

    @Min(100)
    @Max(999)
    private Integer cvv;
}
