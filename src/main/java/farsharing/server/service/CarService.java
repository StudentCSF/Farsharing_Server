package farsharing.server.service;

import farsharing.server.component.AddCarValidationComponent;
import farsharing.server.component.UpdateCarValidationComponent;
import farsharing.server.exception.*;
import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.dto.request.UpdateCarRequest;
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

    private final UpdateCarValidationComponent updateCarValidationComponent;

    @Autowired
    public CarService(CarRepository carRepository, BodyTypeRepository bodyTypeRepository, LocationRepository locationRepository, ColorRepository colorRepository, AddCarValidationComponent addCarValidationComponent, UpdateCarValidationComponent updateCarValidationComponent) {
        this.carRepository = carRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.locationRepository = locationRepository;
        this.colorRepository = colorRepository;
        this.addCarValidationComponent = addCarValidationComponent;
        this.updateCarValidationComponent = updateCarValidationComponent;
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

    public void removeCar(UUID uid) {
        this.carRepository.deleteById(uid);
    }

    public void updateCar(UpdateCarRequest updateCarRequest, UUID uid) {
        if (!this.updateCarValidationComponent.isValid(updateCarRequest)) {
            throw new RequestNotValidException();
        }
        CarEntity carEntity = this.carRepository.findById(uid)
                .orElseThrow(CarNotFoundException::new);

        carEntity.setBodyType(this.bodyTypeRepository.findById(updateCarRequest.getBodyType())
                .orElseThrow(BodyTypeNotFoundException::new));

        carEntity.setPricePerHour(updateCarRequest.getPricePerHour());

        carEntity.setBrand(updateCarRequest.getBrand());

        carEntity.setColor(this.colorRepository.findById(updateCarRequest.getColor())
                .orElseThrow(ColorNotFoundException::new));

        carEntity.setLocation(this.locationRepository.findById(updateCarRequest.getLocation())
                .orElse(null));

        carEntity.setMileage(updateCarRequest.getMileage());

        carEntity.setModel(updateCarRequest.getModel());

        carEntity.setIsAvailable(updateCarRequest.getIsAvailable());

        this.carRepository.save(carEntity);
    }
}
