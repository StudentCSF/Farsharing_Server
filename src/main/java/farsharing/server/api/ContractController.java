package farsharing.server.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Контроллер контрактов", description = "Позволяет добавлять контракты и изменять их состояние")
public class ContractController {

    //TODO ContractService, DTOs
}
