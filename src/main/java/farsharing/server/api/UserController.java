package farsharing.server.api;

import farsharing.server.model.dto.UserDto;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.enumerate.UserRole;
import farsharing.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user")
    public void registerUser(@RequestBody UserDto userDto) {
        this.userService.addUser(userDto);
    }

    @PostMapping("/api/user/auth")
    public String authorizeUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = this.userService.findUser(userDto);
        if (userEntity.getRole() == UserRole.ADMIN) {
            return "Hello, admin";
        }
        return "Auth successful";
        //TODO redirect to cars
    }
}
