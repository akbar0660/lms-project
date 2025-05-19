package az.edu.turing.msstudent.messaging;

import az.edu.turing.msstudent.config.RabbitMQConfig;
import az.edu.turing.msstudent.model.AttendanceChangedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentEventProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendAttendanceChangedEvent(AttendanceChangedMessage attendanceChangedMessage) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ATTENDANCE_CHANGED_EXCHANGE,
                RabbitMQConfig.ATTENDANCE_CHANGED_ROUTING_KEY,
                attendanceChangedMessage
        );
    }
}
