package bantads.account_query.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.account_query.dto.AccountDTO;
import bantads.account_query.entity.Account;
import bantads.account_query.service.AccountService;

@Service
public class RabbitMQConsumer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

  @Autowired
  private AccountService accountService;

  @Autowired 
  private ObjectMapper objectMapper;

  @RabbitListener(queues = {"${rabbitmq.createaccount.queue.name}"})
  public void consumeCreateQueue(String accountDTOMessage){
    LOGGER.info(String.format("Received message -> %s", accountDTOMessage.toString()));
    try{
      AccountDTO accountDTO = objectMapper.readValue(accountDTOMessage, AccountDTO.class);
      AccountDTO accountDTOCreated = accountService.createAccount(accountDTO);
      LOGGER.info(String.format("Account with id %d was created!", accountDTOCreated.getId()));
    } catch (Exception e){
      LOGGER.info(String.format("Error -> %s", e.getMessage()));
    }
  }

  @RabbitListener(queues = {"${rabbitmq.updateaccount.queue.name}"})
  public void consumeUpdateQueue(String accountDTOMessage){
    LOGGER.info(String.format("Received message -> %s", accountDTOMessage.toString()));
    try{
      AccountDTO accountDTO = objectMapper.readValue(accountDTOMessage, AccountDTO.class);
      AccountDTO accountDTOUpdated = accountService.updateAccount(accountDTO);
      LOGGER.info(String.format("Account with id %d was created!", accountDTOUpdated.getId()));
    } catch (Exception e){
      LOGGER.info(String.format("Error -> %s", e.getMessage()));
    }
  }
}
