package bantads.account_command.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class AccountInsertReplyConfig {

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
}