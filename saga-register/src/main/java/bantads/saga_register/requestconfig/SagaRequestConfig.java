package bantads.saga_register.requestconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class SagaRequestConfig {

    @Bean
    public TopicExchange receiverTopic() {
        return new TopicExchange("registerRequest");
    }

    @Bean
    public Queue clientRequestQueue() {
        return new Queue("clientRequestQueue");
    }

    @Bean
    public Queue authRequestQueue() {
        return new Queue("authRequestQueue");
    }

    @Bean
    public Queue accountRequestQueue() {
        return new Queue("accountRequestQueue");
    }

    @Bean
    public Binding clientRequestBinding(TopicExchange receiverTopic, Queue clientRequestQueue) {
        return BindingBuilder.bind(clientRequestQueue).to(receiverTopic).with("clientRequest");
    }

    @Bean
    public Binding authRequestBinding(TopicExchange receiverTopic, Queue authRequestQueue) {
        return BindingBuilder.bind(authRequestQueue).to(receiverTopic).with("authRequest");
    }

    @Bean
    public Binding accountRequestBinding(TopicExchange receiverTopic, Queue accountRequestQueue) {
        return BindingBuilder.bind(accountRequestQueue).to(receiverTopic).with("accountRequest");
    }
}