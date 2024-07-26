package bantads.saga_register.replyconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class SagaReplyConfig {

    @Bean
    public TopicExchange senderTopic() {
        return new TopicExchange("registerReply");
    }

    @Bean
    public Queue clientReplyQueue() {
        return new Queue("clientReplyQueue");
    }

    @Bean
    public Queue authReplyQueue() {
        return new Queue("authReplyQueue");
    }

    @Bean
    public Queue accountReplyQueue() {
        return new Queue("accountReplyQueue");
    }

    @Bean
    public Binding clientReplyBinding(TopicExchange senderTopic, Queue clientReplyQueue) {
        return BindingBuilder.bind(clientReplyQueue).to(senderTopic).with("clientReply");
    }

    @Bean
    public Binding authReplyBinding(TopicExchange senderTopic, Queue authReplyQueue) {
        return BindingBuilder.bind(authReplyQueue).to(senderTopic).with("authReply");
    }

    @Bean
    public Binding accountReplyBinding(TopicExchange senderTopic, Queue accountReplyQueue) {
        return BindingBuilder.bind(accountReplyQueue).to(senderTopic).with("accountReply");
    }
}