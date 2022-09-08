package farsharing.server.api;

import farsharing.server.model.dto.request.UserRequest;
import farsharing.server.model.dto.response.IAuthResponse;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Operation(summary = "Активация учетной записи",
            description = "Позволяет активировать учетную запись")
    public boolean activateAccount(
            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid,
            @PathVariable("code") @Parameter(description = "Переданный пользователем код активации") Integer code
    ) {
        return this.userService.activateAccount(uid, code);
    }

//    @GetMapping("/api/user/{uid}")
//    @Operation(summary = "Получение данных учетной записи пользователя",
//            description = "Позволяет обновить данные о клиенте")
//    public UserEntity getUser(
//            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid
//    ) {
//        return this.userService.getUser(uid);
//    }

    @PutMapping("/api/user/{uid}")
    @Operation(summary = "Обновление учетных данных пользователя",
            description = "Позволяет обновить учетные данные пользователя")
    public void updateUser(
            @RequestBody UserRequest userRequest,
            @PathVariable("uid") @Parameter(description = "Идентификатор пользователя") UUID uid
    ) {
        this.userService.updateUser(userRequest, uid);
    }

    @PostMapping("/api/user/auth")
    @Operation(summary = "Авторизация",
            description = "Позволяет авторизоваться в приложении")
    public IAuthResponse auth(@RequestBody UserRequest userRequest) {
        return this.userService.auth(userRequest, false);
    }

    @GetMapping("/api/user/{pageNumber}")
    @Operation(summary = "Получение определенной страницы с клиентами",
            description = "Позволяет получить запрошенную страницу с клиентами")
    public Page<ClientEntity> getClients(
            @PathVariable("pageNumber")
            @Parameter(description = "Номер страницы (нумерация начинается с нуля)")
                    Integer pageNumber
    ) {
        return this.userService.getClients(pageNumber);
    }
}
