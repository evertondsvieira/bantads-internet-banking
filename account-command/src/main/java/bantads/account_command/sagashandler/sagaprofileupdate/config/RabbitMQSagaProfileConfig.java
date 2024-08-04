package bantads.account_command.sagashandler.sagaprofileupdate.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class RabbitMQSagaProfileConfig {
	
	final private String exchange = "saga.profile.update.exchange";

	final private String updateAccountQueueName = "update.account.queue";

	final private String updateAcccountResponseQueueName = "update.account.response.queue";
	
	final private String updateAccountRoutingKey = "update.account";

	final private String updateAccountResponseRoutingKey = "update.account.response";

	@Bean
	public Queue sagaUpdateAccountQueue(){
		return new Queue(updateAccountQueueName);
	}

	@Bean
	public Queue sagaUpdateAccountResponseQueue(){
		return new Queue(updateAcccountResponseQueueName);
	}

	@Bean
	public  TopicExchange sagaExchange(){
		return new TopicExchange(exchange);
	}

	@Bean 
	public Binding sagaUpdateAccountBinding(){
		return BindingBuilder
							.bind(sagaUpdateAccountQueue())
							.to(sagaExchange())
							.with(updateAccountRoutingKey);				
	}

	@Bean 
	public Binding sagaUpdateAccountResponseBinding(){
		return BindingBuilder
							.bind(sagaUpdateAccountResponseQueue())
							.to(sagaExchange())
							.with(updateAccountResponseRoutingKey);				
	}
}
