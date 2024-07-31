package bantads.saga_manager.requestconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestConfig {
    @Bean
    public TopicExchange receiverManagerTopic() {
        return new TopicExchange("sagaManagerRequest");
    }

    @Bean
    public Queue managerInsertRequestQueue() {
        return new Queue("managerInsertRequestQueue");
    }

    @Bean
    public Binding managerInsertRequestBinding(TopicExchange receiverManagerTopic, Queue managerInsertRequestQueue) {
        return BindingBuilder.bind(managerInsertRequestQueue).to(receiverManagerTopic).with("managerInsertRequest");
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
    public Queue accountInsertRequestQueue() {
        return new Queue("accountInsertRequestQueue");
    }

    @Bean
    public Binding accountInsertRequestBinding(TopicExchange receiverManagerTopic, Queue accountInsertRequestQueue) {
        return BindingBuilder.bind(accountInsertRequestQueue).to(receiverManagerTopic).with("accountInsertRequest");
    }

    @Bean
    public Queue managerDeleteRequestQueue() {
        return new Queue("managerDeleteRequestQueue");
    }

    @Bean
    public Binding managerDeleteRequestBinding(TopicExchange receiverManagerTopic, Queue managerDeleteRequestQueue) {
        return BindingBuilder.bind(managerDeleteRequestQueue).to(receiverManagerTopic).with("managerDeleteRequest");
    }

    @Bean
    public Queue authDeleteRequestQueue() {
        return new Queue("authDeleteRequestQueue");
    }

    @Bean
    public Binding authDeleteRequestBinding(TopicExchange receiverManagerTopic, Queue authDeleteRequestQueue) {
        return BindingBuilder.bind(authDeleteRequestQueue).to(receiverManagerTopic).with("authDeleteRequest");
    }

    @Bean
    public Queue accountDeleteRequestQueue() {
        return new Queue("accountDeleteRequestQueue");
    }

    @Bean
    public Binding accountDeleteRequestBinding(TopicExchange receiverManagerTopic, Queue accountDeleteRequestQueue) {
        return BindingBuilder.bind(accountDeleteRequestQueue).to(receiverManagerTopic).with("accountDeleteRequest");
    }
}