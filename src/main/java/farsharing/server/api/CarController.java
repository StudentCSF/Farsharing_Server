package farsharing.server.api;

import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.dto.response.BriefCarInfoResponse;
import farsharing.server.model.entity.CarEntity;
import farsharing.server.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/api/car")
    public void addCar(@RequestBody AddCarRequest addCarRequest) {
        this.carService.addCar(addCarRequest);
    }

    @GetMapping("/api/car/{uid}")
    public CarEntity getCarDetails(@PathVariable("uid") UUID uid) {
        return this.carService.getCar(uid);
    }

    @GetMapping("/api/cars")
    public List<BriefCarInfoResponse> getCars() {
        List<CarEntity> carEntityList = this.carService.getCars();

        return carEntityList.stream()
                .map(item -> BriefCarInfoResponse.builder()
                        .brand(item.getBrand())
                        .pricePerHour(item.getPricePerHour())
                        .uid(item.getUid())
                        .model(item.getModel())
                        .build())
                .collect(Collectors.toList());
    }
}
