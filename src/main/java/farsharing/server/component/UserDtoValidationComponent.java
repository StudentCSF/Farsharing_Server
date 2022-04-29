package farsharing.server.component;

import farsharing.server.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class UserDtoValidationComponent {

    private final Validator validator;

    @Autowired
    public UserDtoValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> errors = validator.validate(userDto);
        return errors.isEmpty();
    }
}
