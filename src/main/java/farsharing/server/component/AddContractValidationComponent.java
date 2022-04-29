package farsharing.server.component;

import farsharing.server.model.dto.AddContractDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class AddContractValidationComponent {

    private final Validator validator;

    @Autowired
    public AddContractValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(AddContractDto addContractDto) {
        Set<ConstraintViolation<AddContractDto>> errors = validator.validate(addContractDto);
        return errors.isEmpty();
    }
}
