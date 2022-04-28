package farsharing.server.service;

import farsharing.server.repository.UserRoleRepository;
import farsharing.server.model.entity.UserRoleEntity;
import farsharing.server.model.dto.UserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void addUserRole(UserRoleDto userRoleDto) {
        if (this.userRoleRepository.findById(userRoleDto.getName()).isEmpty()) {
            UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                    .name(userRoleDto.getName())
                    .build();
            this.userRoleRepository.save(userRoleEntity);
        }
    }

    public void removeUserRole(UserRoleDto userRoleDto) {
        this.userRoleRepository.deleteById(userRoleDto.getName());
    }

    public List<UserRoleEntity> getAllUserRoles() {
        return this.userRoleRepository.findAll();
    }
}
