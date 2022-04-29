package farsharing.server.service;

import farsharing.server.component.UserDtoValidationComponent;
import farsharing.server.exception.IncorrectPasswordException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.exception.UserAlreadyExistsException;
import farsharing.server.exception.UserNotFoundException;
import farsharing.server.model.dto.UserDto;
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

    private final UserDtoValidationComponent userDtoValidationComponent;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserDtoValidationComponent userDtoValidationComponent) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDtoValidationComponent = userDtoValidationComponent;
    }

    public void addUser(UserDto userDto) {
        if (!this.userDtoValidationComponent.isValid(userDto)) {
            throw new RequestNotValidException();
        }
        if (this.userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        this.userRepository.save(UserEntity.builder()
                .role(UserRole.CLIENT)
                .uid(UUID.randomUUID())
                .email(userDto.getEmail())
                .password(this.bCryptPasswordEncoder.encode(userDto.getPassword()))
                .build());
    }

    public UserEntity findUser(UserDto userDto) {
        if (!this.userDtoValidationComponent.isValid(userDto)) {
            throw new RequestNotValidException();
        }

        UserEntity userEntity = this.userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if (!this.bCryptPasswordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new IncorrectPasswordException();
        }
        return userEntity;
    }
}
