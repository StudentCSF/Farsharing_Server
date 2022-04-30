package farsharing.server.component;

import farsharing.server.model.dto.request.AddCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class AddCarValidationComponent {

    private final Validator validator;

    @Autowired
    public AddCarValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(AddCarRequest addCarRequest) {
        Set<ConstraintViolation<AddCarRequest>> errors = validator.validate(addCarRequest);
        return errors.isEmpty();
    }
}
