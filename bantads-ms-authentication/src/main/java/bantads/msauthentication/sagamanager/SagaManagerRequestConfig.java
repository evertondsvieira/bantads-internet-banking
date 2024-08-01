package bantads.msauthentication.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaManagerRequestConfig {
    @Bean
    public TopicExchange receiverManagerTopic() {
        return new TopicExchange("sagaManagerRequest");
    }

    @Bean
    public Queue authInsertRequestQueue() {
        return new Queue("authInsertRequestQueue");
    }

    @Bean
    public Binding authInsertRequestBinding(TopicExchange receiverManagerTopic, Queue authInsertRequestQueue) {
        return BindingBuilder.bind(authInsertRequestQueue).to(receiverManagerTopic).with("authInsertRequest");
    }

    @Bean
    public Queue authDeleteRequestQueue() {
        return new Queue("authDeleteRequestQueue");
    }

    @Bean
    public Binding authDeleteRequestBinding(TopicExchange receiverManagerTopic, Queue authDeleteRequestQueue) {
        return BindingBuilder.bind(authDeleteRequestQueue).to(receiverManagerTopic).with("authDeleteRequest");
    }
}