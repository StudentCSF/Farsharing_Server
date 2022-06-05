package farsharing.server.api;

import farsharing.server.model.entity.LocationEntity;
import farsharing.server.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Контроллер локаций", description = "Позволяет получать данных о локациях")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/api/locations")
    @Operation(summary = "Получение локаций",
            description = "Позволяет получить имеющиеся в БД локации")
    public List<LocationEntity> getAll() {
        return this.locationService.getAllLocations();
    }
}
