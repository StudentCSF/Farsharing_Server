package farsharing.server.component;

import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.dto.request.AddContractRequest;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class AddContractRequestValidationComponent {

    private final Validator validator;

    public AddContractRequestValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(AddContractRequest addContractRequest) {
        Set<ConstraintViolation<AddContractRequest>> errors = validator.validate(addContractRequest);
        return errors.isEmpty();
    }
}
