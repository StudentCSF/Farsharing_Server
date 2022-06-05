package farsharing.server.service;

import farsharing.server.exception.ClientAlreadyExistsException;
import farsharing.server.exception.ClientNotFoundException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.exception.UserAlreadyExistsException;
import farsharing.server.model.dto.request.ClientRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService service;

    @Test
    void addClient() {
        ClientRequest req = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addClient(req));
    }

    @Test
    void addClient2() {
        ClientRequest req = new ClientRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addClient(req));
    }

    @Test
    void addClient3() {
        ClientRequest req = new ClientRequest();
        req.setAddress("asda");
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setEmail("asd@ad");
        req.setLicense("sdasdadas");
        req.setFirstName("dasda");
        req.setLastName("dsada");
        req.setPassword("dasdas");
        req.setMidName(null);
        req.setValidThru("09/12");
        req.setPhoneNumber("89009009090");
        Assertions.assertThrows(ClientAlreadyExistsException.class, () -> service.addClient(req));
    }

    @Test
    void addClient4() {
        ClientRequest req = new ClientRequest();
        req.setAddress("asda");
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setEmail("asd@ad");
        req.setLicense("sdasdadas12");
        req.setFirstName("dasda");
        req.setLastName("dsada");
        req.setPassword("dasdas");
        req.setMidName(null);
        req.setValidThru("09/12");
        req.setPhoneNumber("89009009090");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> service.addClient(req));
    }

    @Test
    void addClient5() {
        ClientRequest req = new ClientRequest();
        req.setAddress("asda");
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setEmail("asd@ad312");
        req.setLicense("sdasdadas2");
        req.setFirstName("dasda");
        req.setLastName("dsada");
        req.setPassword("dasdas");
        req.setMidName(null);
        req.setValidThru("09/12");
        req.setPhoneNumber("89009009090");
        Assertions.assertDoesNotThrow(() -> service.addClient(req));
    }

    @Test
    void getData() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.getData(uid));
    }

    @Test
    void getData2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.getData(uid));
    }

    @Test
    void getData3() {
        UUID uid = UUID.fromString("de5f606e-260f-4177-83ff-7345e1789b78");
        Assertions.assertDoesNotThrow(() -> service.getData(uid));
    }


    @Test
    void delete() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.delete(uid));
    }

    @Test
    void delete2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.delete(uid));
    }

    @Test
    void delete3() {
        UUID uid = UUID.fromString("a7dcce05-4d2d-4156-815f-cfde8931d6a9");
        Assertions.assertDoesNotThrow(() -> service.delete(uid));
    }

    @Test
    void update() {
        UUID uid = null;
        ClientRequest req = new ClientRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.update(uid, req));
    }

    @Test
    void update2() {
        UUID uid = UUID.randomUUID();
        ClientRequest req = new ClientRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.update(uid, req));
    }

    @Test
    void update3() {
        UUID uid = UUID.randomUUID();
        ClientRequest req = new ClientRequest();
        req.setAddress("asda");
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setEmail("asd@ad312");
        req.setLicense("sdasdadas2");
        req.setFirstName("dasda");
        req.setLastName("dsada");
        req.setPassword("dasdas");
        req.setMidName(null);
        req.setValidThru("09/12");
        req.setPhoneNumber("89009009090");
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.update(uid, req));
    }

    @Test
    void update4() {
        UUID uid = UUID.fromString("2bf52914-04c2-42ee-98b6-63a456ec783c");
        ClientRequest req = new ClientRequest();
        req.setAddress("asda");
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setEmail("asd@ad312");
        req.setLicense("license2");
        req.setFirstName("dasda");
        req.setLastName("dsada");
        req.setPassword("dasdas");
        req.setMidName(null);
        req.setValidThru("09/12");
        req.setPhoneNumber("89009009090");
        Assertions.assertThrows(ClientAlreadyExistsException.class, () -> service.update(uid, req));
    }

    @Test
    void update5() {
        UUID uid = UUID.fromString("2bf52914-04c2-42ee-98b6-63a456ec783c");
        ClientRequest req = new ClientRequest();
        req.setAddress("asda");
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setEmail("asd@ad312");
        req.setLicense("sdasdadas2");
        req.setFirstName("dasda");
        req.setLastName("dsada");
        req.setPassword("dasdas");
        req.setMidName(null);
        req.setValidThru("09/12");
        req.setPhoneNumber("89009009090");
        Assertions.assertDoesNotThrow(() -> service.update(uid, req));
    }

    @Test
    void changeClientStatus() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.changeClientStatus(uid));
    }

    @Test
    void changeClientStatus2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.changeClientStatus(uid));
    }

    @Test
    void changeClientStatus3() {
        UUID uid = UUID.fromString("2bf52914-04c2-42ee-98b6-63a456ec783c");
        Assertions.assertDoesNotThrow(() -> service.changeClientStatus(uid));
    }

    @Test
    void getAll() {
        Assertions.assertDoesNotThrow(() -> service.getAll());
    }
}