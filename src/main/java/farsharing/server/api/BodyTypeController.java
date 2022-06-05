package farsharing.server.api;

import farsharing.server.model.entity.BodyTypeEntity;
import farsharing.server.service.BodyTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Контроллер типов кузова", description = "Позволяте получать списки типов кузова")
public class BodyTypeController {

    private final BodyTypeService bodyTypeService;

    @Autowired
    public BodyTypeController(BodyTypeService bodyTypeService) {
        this.bodyTypeService = bodyTypeService;
    }

    @GetMapping("/api/body_types")
    @Operation(summary = "Получение типов кузова",
            description = "Позволяет получить список типов кузова")
    public List<BodyTypeEntity> getAll() {
        return this.bodyTypeService.getAllBodyTypes();
    }
}
