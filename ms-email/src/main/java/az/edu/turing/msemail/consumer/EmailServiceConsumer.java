package az.edu.turing.msemail.consumer;

import az.edu.turing.msemail.OtpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceConsumer {

    private final MailSender mailSender;

    @RabbitListener(queues = "otp-queue")
    public void handleOtpEvent(OtpEvent event) {
        String emailContent = String.format(
                "Sizin OTP kodunuz: %s. Kod 5 dəqiqə ərzində etibarlıdır.",
                event.otp()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.email());
        message.setSubject("OTP Kodu");
        message.setText(emailContent);

        mailSender.send(message);
    }

}

