package az.edu.turing.msstudent.listener;


import az.edu.turing.msstudent.model.StudentCreatedEvent;
import az.edu.turing.msstudent.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentEventListener {

    private final StudentService studentService;

    @RabbitListener(queues = "student.created.queue")
    public void handleStudentCreated(StudentCreatedEvent event) {
        studentService.createProfileFromEvent(event);
    }
}
