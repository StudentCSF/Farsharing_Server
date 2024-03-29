package farsharing.server.service;

import farsharing.server.component.UserRequestValidationComponent;
import farsharing.server.exception.*;
import farsharing.server.model.dto.request.UserRequest;
import farsharing.server.model.dto.response.*;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.enumerate.UserRole;
import farsharing.server.repository.CarRepository;
import farsharing.server.repository.ClientRepository;
//import farsharing.server.repository.ContractRepository;
import farsharing.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRequestValidationComponent userRequestValidationComponent;

    private final CarRepository carRepository;

//    private final ContractRepository contractRepository;

    private final ClientRepository clientRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRequestValidationComponent userRequestValidationComponent,
                       CarRepository carRepository,
//                       ContractRepository contractRepository,
                       ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRequestValidationComponent = userRequestValidationComponent;
        this.carRepository = carRepository;
//        this.contractRepository = contractRepository;
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

    public IAuthResponse auth(UserRequest userRequest, Boolean newClient) {
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
                    .clients(
                            this.clientRepository.findAll()
                                    .stream()
                                    .filter(x -> x.getUser().getRole() != UserRole.DELETED)
                                    .collect(Collectors.toList())
                    )
                    .build();
            ac = null;
        } else if (user.getRole() == UserRole.CLIENT) {
            if (user.getActivationCode() != null && !newClient) {
                throw new NotConfirmedAccountException();
            }
            ac = AuthClientResponse.builder()
                    .clientUid(this.clientRepository.findByUserUid(user.getUid())
                            .orElseThrow(ClientNotFoundException::new)
                            .getUid())
                    .cars(this.carRepository.findAll())
                    .build();
            aa = null;
        } else {
            return null;
        }
        return IAuthResponse.builder()
                .userUid(user.getUid())
                .authAdminResponse(aa)
                .authClientResponse(ac)
                .build();
    }

    public IAuthPageableResponse authPageable(UserRequest userRequest, Boolean newClient) {
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

        AuthAdminPageableResponse aa;
        AuthClientPageableResponse ac;

        if (user.getRole() == UserRole.ADMIN) {
            aa = AuthAdminPageableResponse.builder()
                    .clients(this.clientRepository.findAll(PageRequest.of(0, 6)))
                    .build();
            ac = null;
        } else if (user.getRole() == UserRole.CLIENT) {
            if (user.getActivationCode() != null && !newClient) {
                throw new NotConfirmedAccountException();
            }
            ac = AuthClientPageableResponse.builder()
                    .clientUid(this.clientRepository.findByUserUid(user.getUid())
                            .orElseThrow(ClientNotFoundException::new)
                            .getUid())
                    .cars(this.carRepository.findAll(PageRequest.of(0, 6)))
                    .build();
            aa = null;
        } else {
            return null;
        }
        return IAuthPageableResponse.builder()
                .userUid(user.getUid())
                .authAdminResponse(aa)
                .authClientResponse(ac)
                .build();
    }

    public Page<ClientEntity> getClients(Integer pageNumber) {
        if (pageNumber == null || pageNumber < 0) {
            throw new RequestNotValidException();
        }
        return this.clientRepository.findAll(PageRequest.of(pageNumber, 6));
    }

    public UserEntity findByLogin(String login) {
        if (login != null && login.length() > 0) {
            return this.userRepository.findByEmail(login).orElseThrow(ClientNotFoundException::new);
        } else throw new RequestNotValidException();
    }
}
