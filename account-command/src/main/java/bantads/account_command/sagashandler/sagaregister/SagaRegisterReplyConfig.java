package bantads.account_command.sagashandler.sagaregister;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class SagaRegisterReplyConfig {

    @Bean
    public TopicExchange senderTopic() {
        return new TopicExchange("accountReply");
    }

    @Bean
    public Queue accountReplyQueue() {
        return new Queue("accountReplyQueue");
    }

    @Bean
    public Binding accountReplyBinding(TopicExchange senderTopic, Queue accountReplyQueue) {
        return BindingBuilder.bind(accountReplyQueue).to(senderTopic).with("accountReply");
    }
}