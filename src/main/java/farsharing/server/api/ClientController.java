package farsharing.server.api;

import farsharing.server.model.dto.request.AddClientRequest;
import farsharing.server.model.dto.response.ClientDataResponse;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Контроллер клиентов", description = "Позволяет добавлять, удалять клиентов и изменять их данные")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/api/client/register")
    @Operation(summary = "Регистрация клиента",
            description = "Позволяет зарегистрировать клиента")
    public void addClient(@RequestBody AddClientRequest addClientRequest) {
        this.clientService.addClient(addClientRequest);
    }

//    @GetMapping("/api/client/{uid}")
//    @Operation(summary = "Получение данных клиента",
//            description = "Позволяет получить информацию о клиенте")
//    public ClientEntity getClient(
//            @PathVariable("uid") @Parameter(description = "Идентификатор клиента") UUID uid
//    ) {
//        //TODO
//        return null;
//    }

    @DeleteMapping("/api/client/delete/{client_uid}")
    @Operation(summary = "Удаление клиента",
            description = "Позволяет деактивировать учетную запись клиента")
    public void delete(
            @PathVariable("client_uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        this.clientService.delete(uid);
    }

    @PutMapping("/api/client/{uid}")
    @Operation(summary = "Обновление данных клиента",
            description = "Позволяет обновить данные о клиенте")
    public void updateClient(@RequestBody AddClientRequest addClientRequest,
                             @PathVariable("uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        //TODO
    }

    @GetMapping("/api/client/{client_uid}")
    @Operation(summary = "Получение данных клиента",
            description = "Позволяет получить полную информацию о клиенте")
    public ClientDataResponse getData(
            @PathVariable(value = "client_uid") @Parameter(description = "Идентификатор клиента") UUID uid) {
        return this.clientService.getData(uid);
    }
}
