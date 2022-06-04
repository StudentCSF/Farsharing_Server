package farsharing.server.model.dto.response;

import farsharing.server.model.entity.enumerate.ClientStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDataResponse {

    private String email;

    private String password;

    private String license;

    private String firstName;

    private String midName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String cardNumber;

    private String validThru;

    private String cvv;

    private Integer accidents;

    private ClientStatus status;

    private Boolean existsActiveContract;
}
