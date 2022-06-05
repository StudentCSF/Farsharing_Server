package farsharing.server.service;

import farsharing.server.exception.*;
import farsharing.server.model.dto.request.AddCarRequest;
import farsharing.server.model.dto.request.UpdateCarRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarService service;

    @Test
    void addCar() {
        AddCarRequest req = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addCar(req));
    }

    @Test
    void addCar2() {
        AddCarRequest req = new AddCarRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addCar(req));
    }

    @Test
    void addCar3() {
        AddCarRequest req = new AddCarRequest();
        req.setBodyType("null");
        req.setBrand("null");
        req.setColor("красный");
        req.setLocation(null);
        req.setMileage(123f);
        req.setPricePerHour(123456f);
        req.setModel("null");
        req.setStateNumber("aaa");
        Assertions.assertThrows(BodyTypeNotFoundException.class, () -> service.addCar(req));
    }

    @Test
    void addCar4() {
        AddCarRequest req = new AddCarRequest();
        req.setBodyType("купе");
        req.setBrand("null");
        req.setColor("серый");
        req.setLocation(UUID.randomUUID());
        req.setMileage(1f);
        req.setPricePerHour(1f);
        req.setModel("null");
        req.setStateNumber("88228");
        Assertions.assertThrows(LocationNotFoundException.class, () -> service.addCar(req));
    }

    @Test
    void addCar5() {
        AddCarRequest req = new AddCarRequest();
        req.setBodyType("купе");
        req.setBrand("null");
        req.setColor("тгдд");
        req.setLocation(null);
        req.setMileage(123f);
        req.setPricePerHour(123456f);
        req.setModel("null");
        req.setStateNumber("aaa");
        Assertions.assertThrows(ColorNotFoundException.class, () -> service.addCar(req));
    }

    @Test
    void addCar6() {
        AddCarRequest req = new AddCarRequest();
        req.setBodyType(null);
        req.setBrand(null);
        req.setColor(null);
        req.setLocation(null);
        req.setMileage(null);
        req.setPricePerHour(null);
        req.setModel(null);
        req.setStateNumber(null);
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addCar(req));
    }

    @Test
    void addCar7() {
        AddCarRequest req = new AddCarRequest();
        req.setBodyType("купе");
        req.setBrand("null");
        req.setColor("серый");
        req.setLocation(null);
        req.setMileage(1f);
        req.setPricePerHour(1f);
        req.setModel("null");
        req.setStateNumber("777");
        Assertions.assertThrows(CarWithSuchStateNumberAlreadyExistsException.class, () -> service.addCar(req));
    }

    @Test
    void addCar8() {
        AddCarRequest req = new AddCarRequest();
        req.setBodyType("купе");
        req.setBrand("null");
        req.setColor("серый");
        req.setLocation(UUID.fromString("5e3d59d9-0f40-4f37-a164-5aa7f3fcd6ea"));
        req.setMileage(1f);
        req.setPricePerHour(1f);
        req.setModel("null");
        req.setStateNumber("888");
        Assertions.assertDoesNotThrow(() -> service.addCar(req));
    }

    @Test
    void getCar() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.getCar(uid));
    }

    @Test
    void getCar2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(CarNotFoundException.class, () -> service.getCar(uid));
    }

    @Test
    void getCar3() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        Assertions.assertNotNull(service.getCar(uid));
    }

    @Test
    void updateCar() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        UpdateCarRequest req = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.updateCar(req, uid));
    }

    @Test
    void updateCar2() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        UpdateCarRequest req = new UpdateCarRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.updateCar(req, uid));
    }

    @Test
    void updateCar3() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        UpdateCarRequest req = new UpdateCarRequest();
        req.setBodyType("тгдд");
        req.setBrand("null");
        req.setColor("wer");
        req.setLocation(null);
        req.setMileage(1f);
        req.setIsAvailable(null);
        req.setPricePerHour(1f);
        req.setModel("wqr");
        Assertions.assertThrows(RequestNotValidException.class, () -> service.updateCar(req, uid));
    }

    @Test
    void updateCar4() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        UpdateCarRequest req = new UpdateCarRequest();
        req.setBodyType("das");
        req.setBrand("null");
        req.setColor("wer");
        req.setLocation(null);
        req.setMileage(1f);
        req.setIsAvailable(false);
        req.setPricePerHour(1f);
        req.setModel("wqr");
        Assertions.assertThrows(BodyTypeNotFoundException.class, () -> service.updateCar(req, uid));
    }

    @Test
    void updateCar5() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        UpdateCarRequest req = new UpdateCarRequest();
        req.setBodyType("купе");
        req.setBrand("null");
        req.setColor("wer");
        req.setLocation(null);
        req.setMileage(1f);
        req.setIsAvailable(false);
        req.setPricePerHour(1f);
        req.setModel("wqr");
        Assertions.assertThrows(ColorNotFoundException.class, () -> service.updateCar(req, uid));
    }

    @Test
    void updateCar6() {
        UUID uid = UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561");
        UpdateCarRequest req = new UpdateCarRequest();
        req.setBodyType("купе");
        req.setBrand("mers");
        req.setColor("серый");
        req.setLocation(UUID.fromString("5e3d59d9-0f40-4f37-a164-5aa7f3fcd6ea"));
        req.setMileage(228f);
        req.setIsAvailable(false);
        req.setPricePerHour(1000f);
        req.setModel("amg");
        Assertions.assertDoesNotThrow(() -> service.updateCar(req, uid));
    }
}