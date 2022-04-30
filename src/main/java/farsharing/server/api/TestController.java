package farsharing.server.api;

//import farsherver.server.model.entity.AdminEntity;

import farsharing.server.model.entity.UserEntity;
import farsharing.server.repository.UserRepository;
//import farsharing.server.repository.UserRoleRepository;
//import farsharing.server.model.entity.UserRoleEntity;
//import farsherver.server.model.entity.embeddable.UserIdEmbeddable;
//import farsherver.server.repository.AdminRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Hidden
public class TestController {

    private UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api")
    List<String> test() {
        return new ArrayList<>(List.of("It", "is", "working"));
    }
}
