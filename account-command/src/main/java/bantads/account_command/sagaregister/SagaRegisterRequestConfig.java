package bantads.account_command.sagaregister;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaRegisterRequestConfig {
    @Bean
    public TopicExchange receiverTopic() {
        return new TopicExchange("registerRequest");
    }

    @Bean
    public Queue accountRequestQueue() {
        return new Queue("accountRequestQueue");
    }

    @Bean
    public Binding accountRequestBinding(TopicExchange receiverTopic, Queue accountRequestQueue) {
        return BindingBuilder.bind(accountRequestQueue).to(receiverTopic).with("accountRequest");
    }
}