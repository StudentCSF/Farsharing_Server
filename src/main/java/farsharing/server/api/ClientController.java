package farsharing.server.api;

import farsharing.server.model.dto.request.AddClientRequest;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.service.ClientService;
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
    public void addClient(@RequestBody AddClientRequest addClientRequest) {
        this.clientService.addClient(addClientRequest);
    }

    @GetMapping("/api/client/{uid}")
    public ClientEntity getClient(
            @PathVariable("uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        //TODO
        return null;
    }

    @DeleteMapping("/api/client/{uid}")
    public void deleteClient(
            @PathVariable("uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        //TODO
    }

    @PutMapping("/api/client/{uid}")
    public void updateClient(@RequestBody AddClientRequest addClientRequest,
                             @PathVariable("uid") @Parameter(description = "Идентификатор клиента") UUID uid
    ) {
        //TODO
    }
}
