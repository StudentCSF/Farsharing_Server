package farsharing.server.service;

import farsharing.server.exception.UserAlreadyExistsException;
import farsharing.server.model.dto.request.AddUserRequest;
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

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void addUser(AddUserRequest addUserRequest) {
        if (this.userRepository.findByEmail(addUserRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        this.userRepository.save(UserEntity.builder()
                .role(UserRole.CLIENT)
                .uid(UUID.randomUUID())
                .email(addUserRequest.getEmail())
                .password(this.bCryptPasswordEncoder.encode(addUserRequest.getPassword()))
                .build());
    }
}
