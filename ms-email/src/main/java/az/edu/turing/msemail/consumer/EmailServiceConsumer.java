package az.edu.turing.msemail.consumer;

import az.edu.turing.msemail.config.RabbitMQConfig;
import az.edu.turing.msemail.event.OtpEvent;
import az.edu.turing.msemail.model.EmailPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceConsumer {

    private final MailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.OTP_QUEUE)
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

    @RabbitListener(queues = RabbitMQConfig.STAFF_ATTENDANCE_NOTIFICATION_QUEUE)
    public void handleStaffAttendanceNotification(EmailPayload payload) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(payload.getTo());
        message.setSubject(payload.getSubject());
        message.setText(payload.getBody());
        mailSender.send(message);
    }

}

