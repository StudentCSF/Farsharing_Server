package farsharing.server.component;

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
        int code = this.randomizer.nextInt(9000) + 1000;
        smm.setText(String.format("Ваш код активации: %d\nПожалуйста, введите его в соответствующем поле в приложении для активации учетной записи",
                code));
        this.mailSender.send(smm);
        return code;
    }
}
