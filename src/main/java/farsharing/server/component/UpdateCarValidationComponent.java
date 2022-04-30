package farsharing.server.component;

import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.dto.request.UpdateCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class UpdateCarValidationComponent {

    private final Validator validator;

    @Autowired
    public UpdateCarValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(UpdateCarRequest updateCarRequest) {
        Set<ConstraintViolation<UpdateCarRequest>> errors = validator.validate(updateCarRequest);
        return errors.isEmpty();
    }
}
