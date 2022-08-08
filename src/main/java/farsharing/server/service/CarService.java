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
    public CarService(CarRepository carRepository,
                      BodyTypeRepository bodyTypeRepository,
                      LocationRepository locationRepository,
                      ColorRepository colorRepository,
                      AddCarValidationComponent addCarValidationComponent,
                      UpdateCarValidationComponent updateCarValidationComponent) {
        this.carRepository = carRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.locationRepository = locationRepository;
        this.colorRepository = colorRepository;
        this.addCarValidationComponent = addCarValidationComponent;
        this.updateCarValidationComponent = updateCarValidationComponent;
    }

    public void addCar(AddCarRequest addCarRequest) {
        if (addCarRequest == null
                || !addCarValidationComponent.isValid(addCarRequest)
        ) {
            throw new RequestNotValidException();
        }

        if (this.carRepository.findByStateNumber(addCarRequest.getStateNumber()).isPresent()) {
            throw new CarWithSuchStateNumberAlreadyExistsException();
        }

        BodyTypeEntity bodyTypeEntity = this.bodyTypeRepository.findById(addCarRequest.getBodyType())
                .orElseThrow(BodyTypeNotFoundException::new);

//        LocationEntity locationEntity = this.locationRepository.findById(addCarRequest.getLocation())
//                .orElseThrow(LocationNotFoundException::new);

        LocationEntity locationEntity = this.locationRepository.save(
                LocationEntity.builder()
                        .uid(UUID.randomUUID())
                        .x(addCarRequest.getXCoord())
                        .y(addCarRequest.getYCoord())
                        .build()
        );

        ColorEntity colorEntity = this.colorRepository.findById(addCarRequest.getColor())
                .orElseThrow(ColorNotFoundException::new);

        CarEntity newCar = CarEntity.builder()
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
                .build();

        this.carRepository.save(newCar);
    }

    public CarEntity getCar(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        return this.carRepository.findById(uid)
                .orElseThrow(CarNotFoundException::new);
    }

    public List<CarEntity> getCars() {
        return this.carRepository.findAll();
    }

    public void removeCar(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        this.carRepository.deleteById(uid);
    }

    public void updateCar(UpdateCarRequest updateCarRequest, UUID uid) {
        if (uid == null
                || updateCarRequest == null
                || !this.updateCarValidationComponent.isValid(updateCarRequest)
        ) {
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


        double x = updateCarRequest.getXCoord(),
                y = updateCarRequest.getYCoord(),
                eps = 1e-3;
        if (Math.abs(x - carEntity.getLocation().getX()) > eps
                || Math.abs(y - carEntity.getLocation().getY()) > eps) {
            carEntity.setLocation(this.locationRepository.save(
                    LocationEntity.builder()
                            .uid(UUID.randomUUID())
                            .x(x)
                            .y(y)
                            .build()
            ));
        }

        carEntity.setMileage(updateCarRequest.getMileage());

        carEntity.setModel(updateCarRequest.getModel());

        carEntity.setIsAvailable(updateCarRequest.getIsAvailable());

        this.carRepository.save(carEntity);
    }
}
