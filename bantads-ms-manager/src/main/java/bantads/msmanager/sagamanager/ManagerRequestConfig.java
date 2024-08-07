package bantads.msmanager.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerRequestConfig {
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
    public Queue managerDeleteRequestQueue() {
        return new Queue("managerDeleteRequestQueue");
    }

    @Bean
    public Binding managerDeleteRequestBinding(TopicExchange receiverManagerTopic, Queue managerDeleteRequestQueue) {
        return BindingBuilder.bind(managerDeleteRequestQueue).to(receiverManagerTopic).with("managerDeleteRequest");
    }

}