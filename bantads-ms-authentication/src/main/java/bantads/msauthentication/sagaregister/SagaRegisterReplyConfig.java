package bantads.msauthentication.sagaregister;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class SagaRegisterReplyConfig {

    @Bean
    public TopicExchange senderTopic() {
        return new TopicExchange("registerReply");
    }

    @Bean
    public Queue authReplyQueue() {
        return new Queue("authReplyQueue");
    }

    @Bean
    public Binding authReplyBinding(TopicExchange senderTopic, Queue authReplyQueue) {
        return BindingBuilder.bind(authReplyQueue).to(senderTopic).with("authReply");
    }
}