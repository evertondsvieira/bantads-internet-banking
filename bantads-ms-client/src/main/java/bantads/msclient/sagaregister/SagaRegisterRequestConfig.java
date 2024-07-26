package bantads.msclient.sagaregister;

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
    public Queue clientRequestQueue() {
        return new Queue("clientRequestQueue");
    }

    @Bean
    public Binding clientRequestBinding(TopicExchange receiverTopic, Queue clientRequestQueue) {
        return BindingBuilder.bind(clientRequestQueue).to(receiverTopic).with("clientRequest");
    }
}