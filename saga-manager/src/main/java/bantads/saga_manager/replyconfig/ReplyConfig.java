package bantads.saga_manager.replyconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplyConfig {
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

    @Bean
    public Queue authInsertReplyQueue() {
        return new Queue("authInsertReplyQueue");
    }    

    @Bean
    public Binding authInsertReplyBinding(TopicExchange senderManagerTopic, Queue authInsertReplyQueue) {
        return BindingBuilder.bind(authInsertReplyQueue).to(senderManagerTopic).with("authInsertReply");
    }

    @Bean
    public Queue accountInsertReplyQueue() {
        return new Queue("accountInsertReplyQueue");
    }    

    @Bean
    public Binding accountInsertReplyBinding(TopicExchange senderManagerTopic, Queue accountInsertReplyQueue) {
        return BindingBuilder.bind(accountInsertReplyQueue).to(senderManagerTopic).with("accountInsertReply");
    }

    @Bean
    public Queue managerDeleteReplyQueue() {
        return new Queue("managerDeleteReplyQueue");
    }

    @Bean
    public Binding managerDeleteReplyBinding(TopicExchange senderManagerTopic, Queue managerDeleteReplyQueue) {
        return BindingBuilder.bind(managerDeleteReplyQueue).to(senderManagerTopic).with("managerDeleteReply");
    }

    @Bean
    public Queue authDeleteReplyQueue() {
        return new Queue("authDeleteReplyQueue");
    }

    @Bean
    public Binding authDeleteReplyBinding(TopicExchange senderManagerTopic, Queue authDeleteReplyQueue) {
        return BindingBuilder.bind(authDeleteReplyQueue).to(senderManagerTopic).with("authDeleteReply");
    }

    @Bean
    public Queue accountDeleteReplyQueue() {
        return new Queue("accountDeleteReplyQueue");
    }

    @Bean
    public Binding accountDeleteReplyBinding(TopicExchange senderManagerTopic, Queue accountDeleteReplyQueue) {
        return BindingBuilder.bind(accountDeleteReplyQueue).to(senderManagerTopic).with("accountDeleteReply");
    }
}