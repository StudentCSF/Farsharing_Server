package farsharing.server.service;

import farsharing.server.exception.*;
import farsharing.server.model.dto.request.AddContractRequest;
import farsharing.server.model.dto.request.PayRequest;
import farsharing.server.model.dto.response.CarResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContractServiceTest {

    @Autowired
    private ContractService service;

    @Test
    void checkCar() {
        UUID uid = null;
        UUID uid2 = UUID.randomUUID();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.checkCar(uid, uid2));
    }

    @Test
    void checkCar2() {
        UUID uid = UUID.randomUUID();
        UUID uid2 = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.checkCar(uid, uid2));
    }

    @Test
    void checkCar3() {
        UUID uid = UUID.randomUUID();
        UUID uid2 = UUID.randomUUID();
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.checkCar(uid, uid2));
    }

    @Test
    void checkCar4() {
        UUID uid = UUID.fromString("2bf52914-04c2-42ee-98b6-63a456ec783c");
        UUID uid2 = UUID.randomUUID();
        Assertions.assertThrows(CarNotFoundException.class, () -> service.checkCar(uid, uid2));
    }

    @Test
    void checkCar5() {
        UUID uid = UUID.fromString("2bf52914-04c2-42ee-98b6-63a456ec783c");
        UUID uid2 = UUID.fromString("d67721d4-74ff-458c-bf08-6a85b242a593");
        Assertions.assertThrows(NotFreeCarNotExistsInContractException.class, () -> service.checkCar(uid, uid2));
    }

    @Test
    void checkCar6() {
        UUID uid = UUID.fromString("de5f606e-260f-4177-83ff-7345e1789b78");
        UUID uid2 = UUID.fromString("f0907024-5e81-400a-96f5-3eb4a248b6fd");
        CarResponse res = service.checkCar(uid, uid2);
        Assertions.assertFalse(res.getIsFree());
        Assertions.assertTrue(res.getThisClient());
    }

    @Test
    void checkCar7() {
        UUID uid = UUID.fromString("fc9d330b-65de-46f6-a116-17e2e1e34142");
        UUID uid2 = UUID.fromString("f80f6516-9cd9-42fe-a45b-3970d67fb4cb");
        CarResponse res = service.checkCar(uid, uid2);
        Assertions.assertTrue(res.getIsFree());
        Assertions.assertTrue(res.getThisClient());
    }

    @Test
    void checkCar8() {
        UUID uid = UUID.fromString("de5f606e-260f-4177-83ff-7345e1789b78");
        UUID uid2 = UUID.fromString("f80f6516-9cd9-42fe-a45b-3970d67fb4cb");
        CarResponse res = service.checkCar(uid, uid2);
        Assertions.assertTrue(res.getIsFree());
        Assertions.assertFalse(res.getThisClient());
    }

    @Test
    void getPayData() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.getPayData(uid));
    }

    @Test
    void getPayData2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.getPayData(uid));
    }

    @Test
    void getPayData3() {
        UUID uid = UUID.fromString("2bf52914-04c2-42ee-98b6-63a456ec783c");
        Assertions.assertDoesNotThrow(() -> service.getPayData(uid));
    }

    @Test
    void pay() {
        UUID uid = null;
        PayRequest req = new PayRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.pay(uid, req));
    }

    @Test
    void pay2() {
        UUID uid = UUID.randomUUID();
        PayRequest req = new PayRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.pay(uid, req));
    }

    @Test
    void pay3() {
        UUID uid = UUID.randomUUID();
        PayRequest req = new PayRequest();
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setValidThru("09/21");
        req.setSavePaymentData(false);
        Assertions.assertThrows(ContractNotFoundException.class, () -> service.pay(uid, req));
    }

    @Test
    void pay4() {
        UUID uid = UUID.fromString("f84b7a88-d757-453a-8dad-78bf6dacf947");
        PayRequest req = new PayRequest();
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setValidThru("09/21");
        req.setSavePaymentData(false);
        Assertions.assertThrows(ContractNotApprovedException.class, () -> service.pay(uid, req));
    }

    @Test
    void pay5() {
        UUID uid = UUID.fromString("f84b7a88-d757-453a-8dad-78bf6dacf947");
        PayRequest req = new PayRequest();
        req.setCvv("123");
        req.setCardNumber("1234123412341234");
        req.setValidThru("09/21");
        req.setSavePaymentData(false);
        Assertions.assertThrows(IncorrectPaymentDataException.class, () -> service.pay(uid, req));
    }

    @Test
    void cancel() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.cancel(uid));
    }

    @Test
    void cancel2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(ContractNotFoundException.class, () -> service.cancel(uid));
    }

    @Test
    void addContract() {
        AddContractRequest req = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addContract(req));
    }

    @Test
    void addContract2() {
        AddContractRequest req = new AddContractRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.addContract(req));
    }

    @Test
    void addContract3() {
        AddContractRequest req = new AddContractRequest();
        req.setCarUid(UUID.randomUUID());
        req.setClientUid(UUID.randomUUID());
        req.setEndTime(ZonedDateTime.now());
        req.setStartTime(ZonedDateTime.now());
        Assertions.assertThrows(CarNotFoundException.class, () -> service.addContract(req));
    }

    @Test
    void addContract4() {
        AddContractRequest req = new AddContractRequest();
        req.setCarUid(UUID.fromString("241d8f9c-b51c-4fe3-93ed-f8b65d08e561"));
        req.setClientUid(UUID.randomUUID());
        req.setEndTime(ZonedDateTime.now());
        req.setStartTime(ZonedDateTime.now());
        Assertions.assertThrows(CarIsNotAvailableException.class, () -> service.addContract(req));
    }

    @Test
    void addContract5() {
        AddContractRequest req = new AddContractRequest();
        req.setCarUid(UUID.fromString("f80f6516-9cd9-42fe-a45b-3970d67fb4cb"));
        req.setClientUid(UUID.randomUUID());
        req.setEndTime(ZonedDateTime.now());
        req.setStartTime(ZonedDateTime.now());
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.addContract(req));
    }

    @Test
    void getRequestInfo() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.getRequestInfo(uid));
    }

    @Test
    void getRequestInfo2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(ContractNotFoundException.class, () -> service.getRequestInfo(uid));
    }

    @Test
    void getRequestInfo3() {
        UUID uid = UUID.fromString("f84b7a88-d757-453a-8dad-78bf6dacf947");
        Assertions.assertDoesNotThrow(() -> service.getRequestInfo(uid));
    }

    @Test
    void getRequestInfo4() {
        UUID uid = UUID.fromString("1711e49a-3644-470b-a2d3-913fc7634050");
        Assertions.assertThrows(ContractHaveNotConsideredStatusException.class, () -> service.getRequestInfo(uid));
    }

    @Test
    void approve() {
        UUID uid = null;
        Boolean f = true;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.approve(uid, f));
    }

    @Test
    void approve2() {
        UUID uid = UUID.randomUUID();
        Boolean f = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.approve(uid, f));
    }

    @Test
    void approve3() {
        UUID uid = UUID.randomUUID();
        Boolean f = true;
        Assertions.assertThrows(ContractNotFoundException.class, () -> service.approve(uid, f));
    }

    @Test
    void approve4() {
        UUID uid = UUID.fromString("f84b7a88-d757-453a-8dad-78bf6dacf947");
        Boolean f = true;
        Assertions.assertThrows(ContractHaveNotConsideredStatusException.class, () -> service.approve(uid, f));
    }
}