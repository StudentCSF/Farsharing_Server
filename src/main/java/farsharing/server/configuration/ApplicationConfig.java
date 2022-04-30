package farsharing.server.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("FarSharing-Server API")
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .email("viktorval2001@gmail.com")
                                                .name("Victor Valeev")
                                )
                );
    }
}
