package farsharing.server.component;

import farsharing.server.model.dto.request.UpdateCarRequest;
import farsharing.server.model.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class UserRequestValidationComponent {

    private final Validator validator;

    @Autowired
    public UserRequestValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(UserRequest userRequest) {
        Set<ConstraintViolation<UserRequest>> errors = validator.validate(userRequest);
        return errors.isEmpty();
    }
}
