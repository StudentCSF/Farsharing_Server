package farsharing.server.service;

import farsharing.server.exception.*;
import farsharing.server.model.dto.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void addUser() {
    }

    @Test
    void setActivationCode() {
        UUID uid = null;
        Integer code = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.setActivationCode(uid, code));
    }

    @Test
    void setActivationCode2() {
        UUID uid = UUID.randomUUID();
        Integer code = 1232;
        Assertions.assertThrows(UserNotFoundException.class, () -> service.setActivationCode(uid, code));
    }

    @Test
    void setActivationCode3() {
        UUID uid = UUID.fromString("393e5d06-19df-4e62-bd4c-af03b3fdbfb7");
        Integer code = null;
        Assertions.assertDoesNotThrow(() -> service.setActivationCode(uid, code));
    }

    @Test
    void setActivationCode4() {
        UUID uid = UUID.fromString("393e5d06-19df-4e62-bd4c-af03b3fdbfb7");
        Integer code = 7639;
        Assertions.assertThrows(UserAlreadyActivatedAccount.class, () -> service.setActivationCode(uid, code));
    }

    @Test
    void activateAccount() {
        UUID uid = null;
        Integer code = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.activateAccount(uid, code));
    }

    @Test
    void activateAccount2() {
        UUID uid = UUID.randomUUID();
        Integer code = 1222;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.activateAccount(uid, code));
    }

    @Test
    void activateAccount3() {
        UUID uid = UUID.randomUUID();
        Integer code = 1222;
        Assertions.assertThrows(UserNotFoundException.class, () -> service.activateAccount(uid, code));
    }

    @Test
    void activateAccount4() {
        UUID uid = UUID.fromString("33f5f3ff-d18c-43d9-8e12-222191737287");
        Integer code = 1222;
        Assertions.assertThrows(UserIsNotClientException.class, () -> service.activateAccount(uid, code));
    }

    @Test
    void activateAccount5() {
        UUID uid = UUID.fromString("33f5f3ff-d18c-43d9-8e12-222191737287");
        Integer code = 1222;
        Assertions.assertFalse(() -> service.activateAccount(uid, code));
    }

    @Test
    void activateAccount6() {
        UUID uid = UUID.fromString("33f5f3ff-d18c-43d9-8e12-222191737287");
        Integer code = 4966;
        Assertions.assertTrue(() -> service.activateAccount(uid, code));
    }

    @Test
    void activateAccount7() {
        UUID uid = UUID.fromString("33f5f3ff-d18c-43d9-8e12-222191737287");
        Integer code = 4966;
        Assertions.assertThrows(UserAlreadyActivatedAccount.class, () -> service.activateAccount(uid, code));
    }

    @Test
    void getUser() {
        UUID uid = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.getUser(uid));
    }

    @Test
    void getUser2() {
        UUID uid = UUID.randomUUID();
        Assertions.assertThrows(UserNotFoundException.class, () -> service.getUser(uid));
    }

    @Test
    void getUser3() {
        UUID uid = UUID.fromString("33f5f3ff-d18c-43d9-8e12-222191737287");
        Assertions.assertDoesNotThrow(() -> service.getUser(uid));
    }

    @Test
    void deleteUser() {
        UUID uid = null;
        UUID uid2 = UUID.randomUUID();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.deleteUser(uid));
        Assertions.assertThrows(UserNotFoundException.class, () -> service.deleteUser(uid2));
    }

    @Test
    void updateUser() {
        UUID uid = null;
        UserRequest req = new UserRequest();
        req.setEmail("qwe");
        req.setPassword("das");
        Assertions.assertThrows(RequestNotValidException.class, () -> service.updateUser(req, uid));
    }

    @Test
    void updateUser2() {
        UUID uid = UUID.randomUUID();
        UserRequest req = new UserRequest();
        req.setEmail("qwe");
        req.setPassword("das");
        Assertions.assertThrows(RequestNotValidException.class, () -> service.updateUser(req, uid));
    }

    @Test
    void updateUser3() {
        UUID uid = UUID.randomUUID();
        UserRequest req = new UserRequest();
        req.setEmail("q@we");
        req.setPassword("das");
        Assertions.assertThrows(UserNotFoundException.class, () -> service.updateUser(req, uid));
    }

    @Test
    void updateUser4() {
        UUID uid = UUID.fromString("33f5f3ff-d18c-43d9-8e12-222191737287");
        UserRequest req = new UserRequest();
        req.setEmail("qmail@com");
        req.setPassword("das");
        Assertions.assertDoesNotThrow(() -> service.updateUser(req, uid));
    }

    @Test
    void updateUser5() {
        UUID uid = UUID.fromString("393e5d06-19df-4e62-bd4c-af03b3fdbfb7");
        UserRequest req = new UserRequest();
        req.setEmail("qmail@com");
        req.setPassword("d2as");
        Assertions.assertThrows(UserWithSuchEmailAlreadyExistsException.class, () -> service.updateUser(req, uid));
    }

    @Test
    void auth() {
        UserRequest req = null;
        Assertions.assertThrows(RequestNotValidException.class, () -> service.auth(req));
    }

    @Test
    void auth2() {
        UserRequest req = new UserRequest();
        Assertions.assertThrows(RequestNotValidException.class, () -> service.auth(req));
    }

    @Test
    void auth3() {
        UserRequest req = new UserRequest();
        req.setEmail("qmail@com");
        req.setPassword("d2as");
        Assertions.assertThrows(WrongPasswordException.class, () -> service.auth(req));
    }

    @Test
    void auth4() {
        UserRequest req = new UserRequest();
        req.setEmail("qmail@com");
        req.setPassword("das");
        Assertions.assertDoesNotThrow(() -> service.auth(req));
    }

    @Test
    void auth5() {
        UserRequest req = new UserRequest();
        req.setEmail("qmail@com");
        req.setPassword("das");
        Assertions.assertThrows(NotConfirmedAccountException.class, () -> service.auth(req));
    }

    @Test
    void auth6() {
        UserRequest req = new UserRequest();
        req.setEmail("qmail@com");
        req.setPassword("das");
        Assertions.assertNull(service.auth(req));
    }

    @Test
    void auth7() {
        UserRequest req = new UserRequest();
        req.setEmail("valeev_v_i@sc.vsu.ru");
        req.setPassword("password");
        Assertions.assertNotNull(service.auth(req).getAuthClientResponse());
    }

    @Test
    void auth8() {
        UserRequest req = new UserRequest();
        req.setEmail("admin@admin.com");
        req.setPassword("admin");
        Assertions.assertNotNull(service.auth(req).getAuthAdminResponse());
    }

}