package bantads.account_command.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountInsertRequestConfig {
    @Bean
    public TopicExchange receiverManagerTopic() {
        return new TopicExchange("sagaManagerRequest");
    }

    @Bean
    public Queue accountInsertRequestQueue() {
        return new Queue("accountInsertRequestQueue");
    }

    @Bean
    public Binding accountInsertRequestBinding(TopicExchange receiverManagerTopic, Queue accountInsertRequestQueue) {
        return BindingBuilder.bind(accountInsertRequestQueue).to(receiverManagerTopic).with("accountInsertRequest");
    }
}