package farsharing.server.component;

import farsharing.server.model.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MailSenderComponent {

    private final JavaMailSender mailSender;

    private final Random randomizer;

    private static final String ACTIVATION_SUBJECT = "Код активации учетной записи в приложении FarSharing";

    private static final String CAR_CODE_SUBJECT = "Код разблокировки автомобиля";

    private static final String FROM = "farsharing.sup-port@yandex.com";

    @Autowired
    public MailSenderComponent(JavaMailSender mailSender,
                               Random randomizer) {
        this.mailSender = mailSender;
        this.randomizer = randomizer;
    }

    public int sendActivationCode(String to) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(FROM);
        smm.setTo(to);
        smm.setSubject(ACTIVATION_SUBJECT);
        int code = this.generateCode();
        smm.setText(String.format("Ваш код активации: %d\nПожалуйста, введите его в соответствующем поле в приложении для активации учетной записи",
                code));
        this.mailSender.send(smm);
        return code;
    }

    public int sendCarCode(CarEntity car, String to) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(FROM);
        smm.setTo(to);
        smm.setSubject(CAR_CODE_SUBJECT);
        Integer code = this.generateCode();
        smm.setText(String.format(
                "Вы успешно оформили в прокат %s %s (госномер %s)!\n\nКод разблокировки - %d",
                car.getBrand(),
                car.getModel(),
                car.getStateNumber(),
                code));
        this.mailSender.send(smm);
        return code;
    }

    private Integer generateCode() {
        return this.randomizer.nextInt(9000) + 1000;
    }
}
