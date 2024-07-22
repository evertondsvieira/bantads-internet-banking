package bantads.account_command.publisher;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.dto.TransactionDTO;
import bantads.account_command.enums.Event;

@Service
public class RabbitMQProducer {
  @Value("${rabbitmq.exchange.name}")
  private String exchange;

	@Value("${rabbitmq.createaccount.routing.name}")
	private String createAccountRouting;

	@Value("${rabbitmq.updateaccount.routing.name}")
	private String updateAccountRouting;

	@Value("${rabbitmq.transaction.routing.name}")
	private String transactionRouting;


  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(AccountDTO account, Event event) {
      LOGGER.info(String.format("Json message sent -> %s", account.toString()));
      Optional<String> routingKey = resolveRouting(event);
      if(routingKey.isEmpty()){
        throw new IllegalArgumentException("Event " + event + " is not supported");
      }
      rabbitTemplate.convertAndSend(exchange, routingKey.get(), account);
  }

  public void sendMessage(TransactionDTO transaction, Event event){
    LOGGER.info(String.format("Json message sent -> %s", transaction.toString()));
    Optional<String> routingKey = resolveRouting(event);
    if(routingKey.isEmpty()){
      throw new IllegalArgumentException("Event " + event + " is not supported");
    }
    rabbitTemplate.convertAndSend(exchange, routingKey.get(), transaction);
  }

  public Optional<String> resolveRouting(Event event){
    if(event == Event.CREATE_ACCOUNT) return Optional.of(createAccountRouting); 
    if(event == Event.UPDATE_ACCOUNT) return Optional.of(updateAccountRouting);
    if(event == Event.TRANSACTION) return Optional.of(transactionRouting);
    return Optional.empty();
  }
}
