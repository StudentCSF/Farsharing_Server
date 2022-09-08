package farsharing.server.api;

import farsharing.server.model.dto.request.ClientRequest;
import farsharing.server.model.dto.response.ClientDataResponse;
import farsharing.server.model.dto.response.IAuthResponse;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public IAuthResponse addClient(@RequestBody ClientRequest clientRequest) {
        return this.clientService.addClient(clientRequest);
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

    @GetMapping("/api/client/{client_uid}/cars")
    @Operation(summary = "Получение идентификаторов забронированных клиентом машин",
            description = "Позволяет получить идентификаторы машин, которые в момент запроса забронированы клиентом")
    public List<UUID> getCurrentClientCars(
            @PathVariable("client_uid") @Parameter(description = "Идентификатор клиента") UUID clientUid
    ) {
        return this.clientService.getCurrentClientAll(clientUid);
    }

    @DeleteMapping("/api/client/delete/{client_uid}")
    @Operation(summary = "Удаление клиента",
            description = "Позволяет деактивировать учетную запись клиента")
    public void delete(
            @PathVariable("client_uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        this.clientService.delete(uid);
    }

    @PutMapping("/api/client/{client_uid}")
    @Operation(summary = "Обновление данных клиента",
            description = "Позволяет обновить данные о клиенте")
    public void updateClient(@RequestBody ClientRequest clientRequest,
                             @PathVariable("client_uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        this.clientService.update(uid, clientRequest);
    }

    @GetMapping("/api/client/{client_uid}")
    @Operation(summary = "Получение данных клиента",
            description = "Позволяет получить полную информацию о клиенте")
    public ClientDataResponse getData(
            @PathVariable(value = "client_uid") @Parameter(description = "Идентификатор клиента") UUID uid) {
        return this.clientService.getData(uid);
    }

    @PutMapping("/api/client/ban/{client_uid}")
    @Operation(summary = "Бан/разбан клиента",
            description = "Позволяет забанить/разбанить клиента")
    public void changeClientStatus(
            @PathVariable(value = "client_uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        this.clientService.changeClientStatus(uid);
    }

    @GetMapping("api/clients")
    @Operation(summary = "Получение списка клиентов",
            description = "Позволяет получить список клиентов из БД")
    public List<ClientEntity> getAll() {
        return this.clientService.getAll();
    }
}
