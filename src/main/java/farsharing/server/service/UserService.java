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
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.ContractRepository;
import farsharing.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRequestValidationComponent userRequestValidationComponent;

    private final CarRepository carRepository;

    private final ContractRepository contractRepository;

    private final ClientRepository clientRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRequestValidationComponent userRequestValidationComponent,
                       CarRepository carRepository,
                       ContractRepository contractRepository,
                       ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRequestValidationComponent = userRequestValidationComponent;
        this.carRepository = carRepository;
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    public UUID addUser(String login, String password) {
        if (login.isBlank() || password.isBlank()) {
            throw new RequestNotValidException();
        }
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
        if (uid == null) {
            throw new RequestNotValidException();
        }
        UserEntity user = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);

        if (user.getActivationCode() == null) {
            throw new UserAlreadyActivatedAccount();
        }

        user.setActivationCode(code);

        this.userRepository.save(user);
    }

    public boolean activateAccount(UUID uid, Integer code) {
        if (uid == null || code == null || code < 1000 || code > 9999) {
            throw new RequestNotValidException();
        }

        UserEntity user = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() != UserRole.CLIENT) {
            throw new UserIsNotClientException();
        }

        if (user.getActivationCode() == null) {
            throw new UserAlreadyActivatedAccount();
        }

        if (user.getActivationCode().equals(code)) {
            user.setActivationCode(null);
            this.userRepository.save(user);
            return true;
        } else return false;
    }

    public UserEntity getUser(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        return this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        UserEntity user = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);
        user.setRole(UserRole.DELETED);
        //user.setEmail(this.bCryptPasswordEncoder.encode(user.getEmail()));
        this.userRepository.save(user);
    }

    public void updateUser(UserRequest userRequest, UUID uid) {
        if (userRequest == null
                || !this.userRequestValidationComponent.isValid(userRequest)
                || uid == null
        ) {
            throw new RequestNotValidException();
        }

        UserEntity userEntity = this.userRepository.findById(uid)
                .orElseThrow(UserNotFoundException::new);

        Optional<UserEntity> u2 = this.userRepository.findByEmail(userRequest.getEmail());
        if (u2.isPresent() && !u2.get().getUid().equals(uid)) {
            throw new UserWithSuchEmailAlreadyExistsException();
        }

        userEntity.setEmail(userRequest.getEmail());

        userEntity.setPassword(this.bCryptPasswordEncoder.encode(userRequest.getPassword()));

        this.userRepository.save(userEntity);
    }

    public IAuthResponse auth(UserRequest userRequest) {
        if (userRequest == null
                || !this.userRequestValidationComponent.isValid(userRequest)
        ) {
            throw new RequestNotValidException();
        }

        UserEntity user = this.userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!this.bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        AuthAdminResponse aa;
        AuthClientResponse ac;

        if (user.getRole() == UserRole.ADMIN) {
            aa = AuthAdminResponse.builder()
                    .contracts(this.contractRepository.findAllByStatus(ContractStatus.CONSIDERED))
                    .build();
            ac = null;
        } else if (user.getRole() == UserRole.CLIENT) {
            if (user.getActivationCode() != null) {
                throw new NotConfirmedAccountException();
            }
            ac = AuthClientResponse.builder()
//                    .uid(this.clientRepository.findByUserUid(user.getUid())
//                            .orElseThrow(ClientNotFoundException::new)
//                            .getUid())
                    .cars(this.carRepository.findAll())
                    .build();
            aa = null;
        } else {
            return null;
        }
        return IAuthResponse.builder()
                .uid(user.getUid())
                .authAdminResponse(aa)
                .authClientResponse(ac)
                .build();
    }
}
