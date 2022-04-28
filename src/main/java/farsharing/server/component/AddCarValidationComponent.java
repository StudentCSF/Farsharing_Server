package farsharing.server.component;

import farsharing.server.model.dto.AddCarDto;
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

    public boolean isValid(AddCarDto addCarDto) {
        Set<ConstraintViolation<AddCarDto>> errors = validator.validate(addCarDto);
        return errors.isEmpty();
    }
}
