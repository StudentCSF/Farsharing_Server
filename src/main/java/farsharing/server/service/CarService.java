package farsharing.server.service;

import farsharing.server.component.AddCarValidationComponent;
import farsharing.server.exception.BodyTypeNotFoundException;
import farsharing.server.exception.CarNotFoundException;
import farsharing.server.exception.ColorNotFoundException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.entity.BodyTypeEntity;
import farsharing.server.model.entity.CarEntity;
import farsharing.server.model.entity.ColorEntity;
import farsharing.server.model.entity.LocationEntity;
import farsharing.server.repository.BodyTypeRepository;
import farsharing.server.repository.CarRepository;
import farsharing.server.repository.ColorRepository;
import farsharing.server.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;

    private final BodyTypeRepository bodyTypeRepository;

    private final LocationRepository locationRepository;

    private final ColorRepository colorRepository;

    private final AddCarValidationComponent addCarValidationComponent;

    @Autowired
    public CarService(CarRepository carRepository, BodyTypeRepository bodyTypeRepository, LocationRepository locationRepository, ColorRepository colorRepository, AddCarValidationComponent addCarValidationComponent) {
        this.carRepository = carRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.locationRepository = locationRepository;
        this.colorRepository = colorRepository;
        this.addCarValidationComponent = addCarValidationComponent;
    }

    public void addCar(AddCarRequest addCarRequest) {
        if (!addCarValidationComponent.isValid(addCarRequest)) {
            throw new RequestNotValidException();
        }

        BodyTypeEntity bodyTypeEntity = this.bodyTypeRepository.findById(addCarRequest.getBodyType())
                .orElseThrow(BodyTypeNotFoundException::new);

        LocationEntity locationEntity = this.locationRepository.findById(addCarRequest.getLocation()).orElse(null);

        ColorEntity colorEntity = this.colorRepository.findById(addCarRequest.getColor())
                .orElseThrow(ColorNotFoundException::new);

        this.carRepository.save(CarEntity.builder()
                .bodyType(bodyTypeEntity)
                .brand(addCarRequest.getBrand())
                .color(colorEntity)
                .isAvailable(true)
                .location(locationEntity)
                .mileage(addCarRequest.getMileage())
                .model(addCarRequest.getModel())
                .pricePerHour(addCarRequest.getPricePerHour())
                .stateNumber(addCarRequest.getStateNumber())
                .uid(UUID.randomUUID())
                .build());
    }

    public CarEntity getCar(UUID uid) {
        return this.carRepository.findById(uid)
                .orElseThrow(CarNotFoundException::new);
    }

    public List<CarEntity> getCars() {
        return this.carRepository.findAll();
    }
}
