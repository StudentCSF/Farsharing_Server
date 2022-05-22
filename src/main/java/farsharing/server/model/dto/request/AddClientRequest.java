package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "Сущность для добавления клиентов")
public class AddClientRequest {

    @Pattern(regexp = "^.+@.+$")
    @Schema(description = "Адрес электронной почты")
    private String email;

    @NotBlank
    @Schema(description = "Пароль")
    private String password;

    @NotBlank
    @Schema(description = "Номер водительской лицензии")
    private String license;

    @NotBlank
    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Отчество")
    private String midName;

    @NotBlank
    @Schema(description = "Фамилия")
    private String lastName;

    @Schema(description = "Адрес")
    private String address;

    @Pattern(regexp = "[1-9][0-9]{10}")
    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @Pattern(regexp = "^([0-9]{16})?$")
    @Schema(description = "Номер кредитной карты")
    private String cardNumber;

    @Pattern(regexp = "^((0[1-9]|1[12])/[0-9]{2})?$")
    @Schema(description = "Срок действия кредитной карты")
    private String validThru;

    @Pattern(regexp = "^([1-9][0-9]{2})?$")
    @Schema(description = "CVV-код кредитной карты")
    private String cvv;
}
