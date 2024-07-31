package bantads.msauthentication.sagamanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class AuthInsertReplyConfig {

    @Bean
    public TopicExchange senderManagerTopic() {
        return new TopicExchange("sagaManagerReply");
    }

    @Bean
    public Queue authInsertReplyQueue() {
        return new Queue("authInsertReplyQueue");
    }

    @Bean
    public Binding authInsertReplyBinding(TopicExchange senderManagerTopic, Queue authInsertReplyQueue) {
        return BindingBuilder.bind(authInsertReplyQueue).to(senderManagerTopic).with("authInsertReply");
    }
}