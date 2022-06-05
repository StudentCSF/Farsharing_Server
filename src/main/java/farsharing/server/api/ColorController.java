package farsharing.server.api;

import farsharing.server.model.entity.ColorEntity;
import farsharing.server.service.ColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Контроллер цветов", description = "Позволяет получать списки цветов")
public class ColorController {

    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/api/colors")
    @Operation(summary = "Получение цветов",
            description = "Позволяет получить цвета из БД")
    public List<ColorEntity> getAll() {
        return this.colorService.getAllColors();
    }
}
