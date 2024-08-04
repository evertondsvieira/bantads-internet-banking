package bantads.account_command.sagashandler.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class SagaManagerReplyConfig {

    @Bean
    public TopicExchange senderManagerTopic() {
        return new TopicExchange("sagaManagerReply");
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
    public Queue accountDeleteReplyQueue() {
        return new Queue("accountDeleteReplyQueue");
    }

    @Bean
    public Binding accountDeleteReplyBinding(TopicExchange senderManagerTopic, Queue accountDeleteReplyQueue) {
        return BindingBuilder.bind(accountDeleteReplyQueue).to(senderManagerTopic).with("accountDeleteReply");
    }
}