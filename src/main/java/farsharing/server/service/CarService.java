package farsharing.server.service;

import farsharing.server.component.AddCarValidationComponent;
import farsharing.server.exception.BodyTypeNotFoundException;
import farsharing.server.exception.ColorNotFoundException;
import farsharing.server.exception.LocationNotFoundException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.AddCarDto;
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

    public void addCar(AddCarDto addCarDto) {
        if (!addCarValidationComponent.isValid(addCarDto)) {
            throw new RequestNotValidException();
        }

        BodyTypeEntity bodyTypeEntity = this.bodyTypeRepository.findById(addCarDto.getBodyType())
                .orElseThrow(BodyTypeNotFoundException::new);

        LocationEntity locationEntity = this.locationRepository.findById(addCarDto.getLocation()).orElse(null);

        ColorEntity colorEntity = this.colorRepository.findById(addCarDto.getColor())
                .orElseThrow(ColorNotFoundException::new);

        this.carRepository.save(CarEntity.builder()
                .bodyType(bodyTypeEntity)
                .brand(addCarDto.getBrand())
                .color(colorEntity)
                .isAvailable(true)
                .location(locationEntity)
                .mileage(addCarDto.getMileage())
                .model(addCarDto.getModel())
                .pricePerHour(addCarDto.getPricePerHour())
                .stateNumber(addCarDto.getStateNumber())
                .uid(UUID.randomUUID())
                .build());
    }
}
