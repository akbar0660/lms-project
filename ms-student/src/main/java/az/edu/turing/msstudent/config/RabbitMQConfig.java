package az.edu.turing.msstudent.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String STUDENT_CREATED_QUEUE = "student.created.queue";
    public static final String STUDENT_CREATED_EXCHANGE = "student.created.exchange";
    public static final String STUDENT_CREATED_ROUTING_KEY = "student.created";

    public static final String ATTENDANCE_CHANGED_EXCHANGE = "attendance.changed.exchange";
    public static final String ATTENDANCE_CHANGED_QUEUE = "attendance.changed.queue";
    public static final String ATTENDANCE_CHANGED_ROUTING_KEY = "attendance.changed";

    @Bean
    public Queue studentCreatedQueue() {
        return new Queue(STUDENT_CREATED_QUEUE, true);
    }

    @Bean
    public DirectExchange studentCreatedExchange() {
        return new DirectExchange(STUDENT_CREATED_EXCHANGE);
    }

    @Bean
    public Binding studentCreatedBinding() {
        return BindingBuilder
                .bind(studentCreatedQueue())
                .to(studentCreatedExchange())
                .with(STUDENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public TopicExchange attendanceChangedExchange() {
        return new TopicExchange(ATTENDANCE_CHANGED_EXCHANGE);
    }

    @Bean
    public Queue attendanceChangedQueue() {
        return new Queue(ATTENDANCE_CHANGED_QUEUE, true);
    }

    @Bean
    public Binding attendanceChangedBinding() {
        return BindingBuilder
                .bind(attendanceChangedQueue())
                .to(attendanceChangedExchange())
                .with(ATTENDANCE_CHANGED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
