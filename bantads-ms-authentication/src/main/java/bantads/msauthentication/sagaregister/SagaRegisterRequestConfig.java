package bantads.msauthentication.sagaregister;

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
    public Queue authRequestQueue() {
        return new Queue("authRequestQueue");
    }

    @Bean
    public Binding authRequestBinding(TopicExchange receiverTopic, Queue authRequestQueue) {
        return BindingBuilder.bind(authRequestQueue).to(receiverTopic).with("authRequest");
    }
}