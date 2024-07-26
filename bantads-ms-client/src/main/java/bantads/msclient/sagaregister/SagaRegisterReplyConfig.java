package bantads.msclient.sagaregister;

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
    public Queue clientReplyQueue() {
        return new Queue("clientReplyQueue");
    }

    @Bean
    public Binding clientReplyBinding(TopicExchange senderTopic, Queue clientReplyQueue) {
        return BindingBuilder.bind(clientReplyQueue).to(senderTopic).with("clientReply");
    }
}