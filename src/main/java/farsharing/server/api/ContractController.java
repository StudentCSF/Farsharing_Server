package farsharing.server.api;

import farsharing.server.model.dto.response.CarResponse;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "Контроллер контрактов", description = "Позволяет добавлять контракты и изменять их состояние")
public class ContractController {

    //TODO ContractService, DTOs

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/api/contract/{cl_uid}/{car_uid}")
    @Operation(summary = "Состояние машины касательно брони",
            description = "Позволяет получить информацию о занятости машины и статусе броне при определенном условии")
    public CarResponse checkCar(
            @PathVariable(name = "cl_uid") @Parameter(description = "Идентификатор клиента") UUID clUid,
            @PathVariable(name = "car_uid") @Parameter(description = "Идентификатор машины") UUID carUid
    ) {
        return this.contractService.checkCar(clUid, carUid);
    }



    @GetMapping("/api/pay/{client_uid}")
    @Operation(summary = "Получение платежных данных",
            description = "Позволяет получить платежные данные клиента, желавшего оплатить бронь")
    public WalletEmbeddable getPayData(
            @PathVariable(value = "client_uid") @Parameter(description = "Идентификатор клиент") UUID uid
    ) {
        return this.contractService.getPayData(uid);
    }
}
