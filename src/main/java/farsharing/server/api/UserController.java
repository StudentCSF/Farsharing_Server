package farsharing.server.api;

import farsharing.server.model.dto.request.UserRequest;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Контроллер пользователей", description = "Позволяет добавлять и удалять пользователей")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/api/user/activate/{uid}/{code}")
    public boolean activateAccount(
            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid,
            @PathVariable("code") @Parameter(description = "Переданный пользователем код активации") Integer code
    ) {
        return this.userService.activateAccount(uid, code);
    }

    @GetMapping("/api/user/{uid}")
    public UserEntity getUser(
            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid
    ) {
        return this.userService.getUser(uid);
    }

    @DeleteMapping("/api/user/{uid}")
    public void deleteUser(
            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid
    ) {
        this.userService.deleteUser(uid);
    }

    @PutMapping("/api/user/{uid}")
    public void updateUser(
            @RequestBody UserRequest userRequest,
            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid
    ) {
        this.userService.updateUser(userRequest, uid);
    }
}
