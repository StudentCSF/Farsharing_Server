package farsharing.server.component;

import farsharing.server.model.dto.AddClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class AddClientValidationComponent {

    private final Validator validator;

    @Autowired
    public AddClientValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(AddClientDto addClientDto) {
        Set<ConstraintViolation<AddClientDto>> errors = validator.validate(addClientDto);
        return errors.isEmpty();
    }
}
