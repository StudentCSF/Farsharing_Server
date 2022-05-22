package farsharing.server.service;

import farsharing.server.component.UserRequestValidationComponent;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.exception.SuchEmailAlreadyExistException;
import farsharing.server.exception.UserAlreadyExistsException;
import farsharing.server.exception.UserNotFoundException;
import farsharing.server.model.dto.request.UserRequest;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.enumerate.UserRole;
import farsharing.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRequestValidationComponent userRequestValidationComponent;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRequestValidationComponent userRequestValidationComponent) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRequestValidationComponent = userRequestValidationComponent;
    }

    public void addUser(UserRequest userRequest) {
        if (!this.userRequestValidationComponent.isValid(userRequest)) {
            throw new RequestNotValidException();
        }

        if (this.userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        this.userRepository.save(UserEntity.builder()
                .role(UserRole.CLIENT)
                .uid(UUID.randomUUID())
                .email(userRequest.getEmail())
                .password(this.bCryptPasswordEncoder.encode(userRequest.getPassword()))
                .build());
    }

    public UserEntity getUser(UUID uid) {
        return this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(UUID uid) {
        UserEntity user = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);
        user.setRole(UserRole.DELETED);
        user.setEmail(this.bCryptPasswordEncoder.encode(user.getEmail()));
        this.userRepository.save(user);
    }

    public void updateUser(UserRequest userRequest, UUID uid) {
        UserEntity userEntity = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);

        UserEntity userEntity2 = this.userRepository.findByEmail(userRequest.getEmail())
                .orElse(null);

        if (userEntity2 != null) {
            throw new SuchEmailAlreadyExistException();
        }

        userEntity.setEmail(userRequest.getEmail());

        userEntity.setPassword(userRequest.getPassword());
    }
}
