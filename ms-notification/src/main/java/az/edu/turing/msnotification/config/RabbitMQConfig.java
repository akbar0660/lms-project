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

    public static final String EXCHANGE_NAME = "auth-exchange";
    public static final String PASSWORD_RESET_NOTIFICATION_QUEUE = "password-reset-notification-queue";

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
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
