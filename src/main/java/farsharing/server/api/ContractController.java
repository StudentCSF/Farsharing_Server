package farsharing.server.api;

import farsharing.server.model.dto.request.AddContractRequest;
import farsharing.server.model.dto.request.PayRequest;
import farsharing.server.model.dto.response.CarResponse;
import farsharing.server.model.dto.response.RequestInfoResponse;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Контроллер контрактов", description = "Позволяет добавлять контракты и изменять их состояние")
public class ContractController {

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

    @PutMapping("/api/pay/{contract_uid}")
    @Operation(summary = "Оплата брони",
            description = "Позволяет провести оплату брони")
    public void pay(
            @PathVariable(value = "contract_uid") @Parameter(description = "Идентификатор контракта/брони") UUID uid,
            @RequestBody PayRequest request) {
        this.contractService.pay(uid, request);
    }

    @GetMapping("/api/contract/cancel/{contract_uid}")
    @Operation(summary = "Отмена брони",
            description = "Позволяет отменить бронь")
    public void cancel(
            @PathVariable(value = "contract_uid") @Parameter(description = "Идентификатор контракта/брони") UUID uid
    ) {
        this.contractService.cancel(uid);
    }

    @PostMapping("api/contract/new")
    @Operation(summary = "Заявка на бронь",
            description = "Позволяет оформить заявку на бронь")
    public UUID request(@RequestBody AddContractRequest addContractRequest) {
        return this.contractService.addContract(addContractRequest);
    }

    @GetMapping("/api/admin/request_info/{contract_uid}")
    @Operation(summary = "Информация о заявке",
            description = "Позволяет получить информацию о заявке и клиенте")
    public RequestInfoResponse getRequestInfo(
            @PathVariable(value = "contract_uid")
            @Parameter(description = "Идентификатор контракта/заявки на бронь")
                    UUID uid
    ) {
        return this.contractService.getRequestInfo(uid);
    }

    @PutMapping("api/admin/approve/{contract_uid}")
    @Operation(summary = "Одобрить заявку",
            description = "Позволяет админу одобрить заявку на бронь")
    public void approve(
            @PathVariable(value = "contract_uid") @Parameter(description = "Идентификатор контракта/брони") UUID uid,
            @RequestBody Boolean approve
    ) {
        this.contractService.approve(uid, approve);
    }
}
