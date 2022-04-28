package farsharing.server.component;

import farsharing.server.model.dto.AddClientDto;
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class AddClientValidationComponent {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final Validator validator;

    @Autowired
    public AddClientValidationComponent(UserRepository userRepository, ClientRepository clientRepository, Validator validator) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.validator = validator;
    }

    public boolean isValid(AddClientDto addClientDto) {
        Set<ConstraintViolation<AddClientDto>> errors = validator.validate(addClientDto);
        return errors.isEmpty();
    }
}
