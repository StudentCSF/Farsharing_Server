package farsharing.server.model.dto.response;

import farsharing.server.model.entity.enumerate.ClientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Сущность с данными о клиенте")
public class ClientDataResponse {

    @Schema(description = "Email учетной записи клиента")
    private String email;

    @Schema(description = "Пароль учетной записи клиента")
    private String password;

    @Schema(description = "Водительская лицензия клиента")
    private String license;

    @Schema(description = "Имя клиента")
    private String firstName;

    @Schema(description = "Отчество клиента")
    private String midName;

    @Schema(description = "Фамилия клиента")
    private String lastName;

    @Schema(description = "Адрес клиента")
    private String address;

    @Schema(description = "Номер телефона клиента")
    private String phoneNumber;

    @Schema(description = "Номер банковской карты клиента")
    private String cardNumber;

    @Schema(description = "Срок действия банковской карты клиента")
    private String validThru;

    @Schema(description = "CVV-код банковской карты клиента")
    private String cvv;

    @Schema(description = "Количество нарушений клиента")
    private Integer accidents;

    @Schema(description = "Статус клиента")
    private ClientStatus status;

    @Schema(description = "Есть ли активный контракт у клиента")
    private Boolean existsActiveContract;
}
