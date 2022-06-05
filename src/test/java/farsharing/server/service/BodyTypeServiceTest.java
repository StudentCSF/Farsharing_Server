package farsharing.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BodyTypeServiceTest {

    @Autowired
    private BodyTypeService service;

    @Test
    void getAllBodyTypes() {
        Assertions.assertNotNull(this.service.getAllBodyTypes());
    }
}