package farsharing.server.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class TestController {

    @GetMapping("/api")
    String test() {
        return "Hello guest!";
    }
}
