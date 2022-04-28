package farsharing.server.service;

import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.AddUserDto;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.UserRoleEntity;
import farsharing.server.repository.UserRepository;
import farsharing.server.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private void addUser(AddUserDto addUserDto) {
        UserRoleEntity userRoleEntity = this.userRoleRepository.findById(addUserDto.getRole()).orElseThrow(() -> new RequestNotValidException("User role does not exist"));
        this.userRepository.save(
                UserEntity.builder()
                        .email(addUserDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(addUserDto.getPassword()))
                        .role(userRoleEntity)
                        .build()
        );
    }

    private void removeUser(UUID userUid) {
        this.userRepository.deleteById(userUid);
    }
}
