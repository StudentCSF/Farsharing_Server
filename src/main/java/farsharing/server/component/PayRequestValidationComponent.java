package farsharing.server.component;

import farsharing.server.model.dto.request.PayRequest;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class PayRequestValidationComponent {

    private final Validator validator;

    public PayRequestValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(PayRequest request) {
        Set<ConstraintViolation<PayRequest>> errors = validator.validate(request);
        return errors.isEmpty();
    }
}
