package farsharing.server.component;

import farsharing.server.model.dto.request.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ClientRequestValidationComponent {

    private final Validator validator;

    @Autowired
    public ClientRequestValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(ClientRequest clientRequest) {
        Set<ConstraintViolation<ClientRequest>> errors = validator.validate(clientRequest);
        return errors.isEmpty();
    }
}
