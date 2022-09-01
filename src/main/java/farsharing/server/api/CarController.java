package farsharing.server.api;

import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.dto.request.UpdateCarRequest;
//import farsharing.server.model.dto.response.BriefCarInfoResponse;
import farsharing.server.model.entity.CarEntity;
import farsharing.server.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
//import java.util.stream.Collectors;

@RestController
@Tag(name = "Контроллер автомобилей", description = "Позволяет добавлять и удалять машины и обновлять их данные ")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/api/car")
    @Operation(
            summary = "Добавление машины",
            description = "Позволяет добавлять машины"
    )
    public void addCar(@RequestBody AddCarRequest addCarRequest) {
        this.carService.addCar(addCarRequest);
    }

    @GetMapping("/api/car/{uid}")
    @Operation(
            summary = "Получение данных машины",
            description = "Позволяет получить полную информацию о машине"
    )
    public CarEntity getCarDetails(
            @PathVariable("uid") @Parameter(description = "Идентификатор машины") UUID uid
    ) {
        return this.carService.getCar(uid);
    }

    @GetMapping("/api/cars")
    @Operation(
            summary = "Получение списка машин",
            description = "Позволяет получить список всех машин"
    )
    public List<CarEntity> getCars() {
        return this.carService.getCars();

//        return carEntityList.stream()
//                .map(item -> BriefCarInfoResponse.builder()
//                        .brand(item.getBrand())
//                        .isAvailable(item.getIsAvailable())
//                        .stateNumber(item.getStateNumber())
//                        .model(item.getModel())
//                        .build())
//                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/car/{uid}")
    @Operation(
            summary = "Удаление машины",
            description = "Позволяет удалить машину"
    )
    public void deleteCar(
            @PathVariable("uid") @Parameter(description = "Идентификатор машины") UUID uid
    ) {
        this.carService.removeCar(uid);
    }

    @PutMapping("/api/car/{uid}")
    @Operation(
            summary = "Обновление данных машины",
            description = "Позволяет обновить данные конкретной машины"
    )
    public void updateCar(
            @RequestBody UpdateCarRequest updateCarRequest,
            @PathVariable @Parameter(description = "Идентификатор машины") UUID uid
    ) {
        this.carService.updateCar(updateCarRequest, uid);
    }
}
