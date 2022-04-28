package farsharing.server.api;

//import farsherver.server.model.entity.AdminEntity;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.repository.UserRepository;
import farsharing.server.repository.UserRoleRepository;
import farsharing.server.model.entity.UserRoleEntity;
//import farsherver.server.model.entity.embeddable.UserIdEmbeddable;
//import farsherver.server.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

;

@RestController
public class TestController {

    private UserRoleRepository userRoleRepository;

    private UserRepository userRepository;

//    private AdminRepository adminRepository;

    @Autowired
    public TestController(UserRoleRepository userRoleRepository, UserRepository userRepository/*, AdminRepository adminRepository*/) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
//        this.adminRepository = adminRepository;
    }

    @GetMapping("/123123")
    void test1() {
        this.userRoleRepository.save(UserRoleEntity.builder().name("Admin").build());
        //return userRoleRepository.findById("Admin").orElseThrow(RuntimeException::new);
        this.userRepository.save(UserEntity.builder()
                .email("qeqwe")
                .password("eqweqwe")
                .role(this.userRoleRepository.findById("Admin").orElseThrow(RuntimeException::new))
                .build());
        UUID uid = this.userRepository.findAll().get(0).getUid();
//        this.adminRepository.save(AdminEntity.builder()
//                .user(
//                        this.userRepository
//                                .findAll()
//                                .get(0))
//                .build());
    }

    @GetMapping("/api")
    List<String> test() {
        return new ArrayList<>(List.of("It", "is", "working"));
    }
}
