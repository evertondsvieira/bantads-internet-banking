package bantads.msmanager.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerInsertReplyConfig {
    @Bean
    public TopicExchange senderManagerTopic() {
        return new TopicExchange("sagaManagerReply");
    }

    @Bean
    public Queue managerInsertReplyQueue() {
        return new Queue("managerInsertReplyQueue");
    }

    @Bean
    public Binding managerInsertReplyBinding(TopicExchange senderManagerTopic, Queue managerInsertReplyQueue) {
        return BindingBuilder.bind(managerInsertReplyQueue).to(senderManagerTopic).with("managerInsertReply");
    }
}