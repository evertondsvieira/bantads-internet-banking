package bantads.saga_profile_update.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class RabbitMQConfig {
	
	final private String exchange = "saga.profile.update.exchange";

	final private String updateClientQueueName = "update.client.queue";

	final private String updateClientResponseQueueName = "update.client.response.queue";

	final private String updateAccountQueueName = "update.account.queue";

	final private String updateAcccountResponseQueueName = "update.account.response.queue";
	
	final private String updateClientRoutingKey = "update.client";

	final private String updateClientResponseRoutingKey = "update.client.response";

	final private String updateAccountRoutingKey = "update.account";

	final private String updateAccountResponseRoutingKey = "update.account.response";


	@Bean
	public Queue updateClientQueue(){
		return new Queue(updateClientQueueName);
	}

	@Bean
	public Queue updateClientResponseQueue(){
		return new Queue(updateClientResponseQueueName);
	}

	@Bean
	public Queue updateAccountQueue(){
		return new Queue(updateAccountQueueName);
	}

	@Bean
	public Queue updateAccountResponseQueue(){
		return new Queue(updateAcccountResponseQueueName);
	}

	@Bean
	public  TopicExchange exchange(){
		return new TopicExchange(exchange);
	}

	@Bean 
	public Binding updateClientBinding(){
		return BindingBuilder
							.bind(updateClientQueue())
							.to(exchange())
							.with(updateClientRoutingKey);				
	}


	@Bean 
	public Binding updateClientResponseBinding(){
		return BindingBuilder
							.bind(updateClientResponseQueue())
							.to(exchange())
							.with(updateClientResponseRoutingKey);				
	}

	@Bean 
	public Binding updateAccountBinding(){
		return BindingBuilder
							.bind(updateAccountQueue())
							.to(exchange())
							.with(updateAccountRoutingKey);				
	}


	@Bean 
	public Binding updateAccountResponseBinding(){
		return BindingBuilder
							.bind(updateAccountResponseQueue())
							.to(exchange())
							.with(updateAccountResponseRoutingKey);				
	}

	@Bean
	public MessageConverter converter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
}
