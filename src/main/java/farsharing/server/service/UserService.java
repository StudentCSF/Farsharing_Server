package farsharing.server.service;

import farsharing.server.component.UserRequestValidationComponent;
import farsharing.server.exception.*;
import farsharing.server.model.dto.request.UserRequest;
import farsharing.server.model.dto.response.AuthAdminResponse;
import farsharing.server.model.dto.response.AuthClientResponse;
import farsharing.server.model.dto.response.IAuthResponse;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.enumerate.ContractStatus;
import farsharing.server.model.entity.enumerate.UserRole;
import farsharing.server.repository.CarRepository;
import farsharing.server.repository.ContractRepository;
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

    private final CarRepository carRepository;

    private final ContractRepository contractRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRequestValidationComponent userRequestValidationComponent,
                       CarRepository carRepository,
                       ContractRepository contractRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRequestValidationComponent = userRequestValidationComponent;
        this.carRepository = carRepository;
        this.contractRepository = contractRepository;
    }

    public UUID addUser(String login, String password) {
        if (this.userRepository.findByEmail(login).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        UUID uuid = UUID.randomUUID();
        this.userRepository.save(UserEntity.builder()
                .role(UserRole.CLIENT)
                .uid(uuid)
                .email(login)
                .activationCode(-1)
                .password(this.bCryptPasswordEncoder.encode(password))
                .build());

        return uuid;
    }

    public void setActivationCode(UUID uid, Integer code) {
        UserEntity user = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);

        if (user.getActivationCode() == null) {
            throw new UserAlreadyActivatedAccount();
        }

        user.setActivationCode(code);

        this.userRepository.save(user);
    }

    public boolean activateAccount(UUID uid, Integer code) {
        if (code < 1000 || code > 9999) {
            throw new RequestNotValidException();
        }

        UserEntity user = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);

        if (user.getActivationCode().equals(code)) {
            user.setActivationCode(null);
            this.userRepository.save(user);
            return true;
        } else return false;
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

    public IAuthResponse auth(UserRequest userRequest) {
        if (!this.userRequestValidationComponent.isValid(userRequest)) {
            throw new RequestNotValidException();
        }

        UserEntity user = this.userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!this.bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        if (user.getRole() == UserRole.ADMIN) {
            return AuthAdminResponse.builder()
                    .contracts(this.contractRepository.findAllByStatus(ContractStatus.CONSIDERED))
                    .build();
        } else if (user.getRole() == UserRole.CLIENT && user.getActivationCode() == null) {
            return AuthClientResponse.builder()
                    .cars(this.carRepository.findAll())
                    .build();
        } else {
            return null;
        }
    }
}
