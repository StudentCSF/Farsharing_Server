package farsharing.server.model.dto.request;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Сущность для добавления клиентов")
public class AddClientRequest {

    @NotNull
    @Schema(description = "Идентификатор пользователя (не клиента)")
    private UUID UserUid;

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

    @NotBlank
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
    private Integer cvv;
}
