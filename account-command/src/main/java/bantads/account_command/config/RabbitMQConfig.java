package bantads.account_command.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.createaccount.queue.name}")
	private String createAccountQueue;

	@Value("${rabbitmq.updateaccount.queue.name}")
	private String updateAccountQueue;

	@Value("${rabbitmq.queue.transaction.queue.name}")
	private String transactionQueue;

	@Value("${rabbitmq.createaccount.routing.name}")
	private String createAccountRouting;

	@Value("${rabbitmq.updateaccount.routing.name}")
	private String updateAccountRouting;

	@Value("${rabbitmq.transaction.routing.name}")
	private String transactionRouting;

	@Bean
	public Queue createAccountQueue(){
		return new Queue(createAccountQueue);
	}

	@Bean
	public Queue updateAccountQueue(){
		return new Queue(updateAccountQueue);
	}

	@Bean
	public Queue transactionQueue(){
		return new Queue(transactionQueue);
	}

	@Bean
	public  TopicExchange exchange(){
		return new TopicExchange(exchange);
	}

	@Bean 
	public Binding createAccountBinding(){
		return BindingBuilder
							.bind(createAccountQueue())
							.to(exchange())
							.with(createAccountRouting);				
	}

	@Bean 
	public Binding updateAccountBinding(){
		return BindingBuilder
							.bind(updateAccountQueue())
							.to(exchange())
							.with(updateAccountRouting);				
	}

	@Bean 
	public Binding transactionBinding(){
		return BindingBuilder
							.bind(transactionQueue())
							.to(exchange())
							.with(transactionRouting);				
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
