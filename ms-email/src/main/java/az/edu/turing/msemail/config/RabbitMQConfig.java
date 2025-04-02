package az.edu.turing.msemail.config;

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
    public static final String OTP_QUEUE = "otp-queue";

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue otpQueue() {
        return new Queue(OTP_QUEUE, true);
    }

    @Bean
    public Binding otpBinding(Queue otpQueue, TopicExchange authExchange) {
        return BindingBuilder.bind(otpQueue)
                .to(authExchange)
                .with("otp.event");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
