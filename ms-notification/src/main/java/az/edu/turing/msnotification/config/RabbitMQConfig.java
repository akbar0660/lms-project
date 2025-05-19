package az.edu.turing.msnotification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //ms-auth
    public static final String EXCHANGE_NAME = "auth-exchange";
    public static final String PASSWORD_RESET_NOTIFICATION_QUEUE = "password-reset-notification-queue";

    //ms-studentden gelen queue
    public static final String ATTENDANCE_CHANGED_QUEUE = "attendance.changed.queue";

    // ms-notification ms-email servisine gondersin deye
    public static final String EMAIL_EXCHANGE = "email-exchange";
    public static final String STAFF_ATTENDANCE_NOTIFICATION_QUEUE = "staff.attendance.notification.queue";
    public static final String STAFF_ATTENDANCE_NOTIFICATION_ROUTING_KEY = "staff.attendance.notification";

    @Bean
    public Queue attendanceChangedQueue() {
        return new Queue(ATTENDANCE_CHANGED_QUEUE, true);
    }

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue passwordResetNotificationQueue() {
        return new Queue(PASSWORD_RESET_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding passwordResetNotificationBinding(Queue passwordResetNotificationQueue, TopicExchange authExchange) {
        return BindingBuilder.bind(passwordResetNotificationQueue)
                .to(authExchange)
                .with("password.reset.notification");
    }

    @Bean
    public Queue staffAttendanceNotificationQueue() {
        return new Queue(STAFF_ATTENDANCE_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding staffAttendanceNotificationBinding(Queue staffAttendanceNotificationQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(staffAttendanceNotificationQueue)
                .to(emailExchange)
                .with(STAFF_ATTENDANCE_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
