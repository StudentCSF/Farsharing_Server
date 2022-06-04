package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Schema(description = "Сущность для проведения оплаты")
public class PayRequest {

    @NotNull
    @Pattern(regexp = "^[0-9]{16}$")
    @Schema(description = "Номер кредитной карты")
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$")
    @Schema(description = "Срок действия кредитной карты")
    private String validThru;

    @NotNull
    @Pattern(regexp = "^[1-9][0-9]{2}$")
    @Schema(description = "CVV-код кредитной карты")
    private String cvv;

    @NotNull
    private Boolean savePaymentData;
}
