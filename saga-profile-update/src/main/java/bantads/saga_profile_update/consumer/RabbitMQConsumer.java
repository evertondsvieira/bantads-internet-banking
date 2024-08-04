package bantads.saga_profile_update.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.saga_profile_update.dto.AccountDTO;
import bantads.saga_profile_update.dto.ClientDTO;
import bantads.saga_profile_update.service.SagaService;

@Service
public class RabbitMQConsumer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

  @Autowired
  private SagaService sagaService;

  @RabbitListener(queues = "update.client.response.queue")
  public void handleClientResponse(ClientDTO clientDTO){
    LOGGER.info(String.format("Received message -> %s", clientDTO.toString()));
    try{
      LOGGER.info(String.format("Client Was updated succefully", clientDTO.toString()));
      AccountDTO accountDTO = new AccountDTO();
      accountDTO.setClient(clientDTO);
      accountDTO.setSalary(clientDTO.getSalary());
      accountDTO.setSituation(clientDTO.getSituation());
      sagaService.updateAccount(accountDTO);
    } catch (Exception e){
      LOGGER.info(String.format("Error -> %s", e.getMessage()));
    }
  }

  @RabbitListener(queues = "update.account.response.queue")
  public void handleAccountResponse(AccountDTO accountDTO){
    LOGGER.info(String.format("Received message -> %s", accountDTO.toString()));
    try{
      LOGGER.info(String.format("Account with id %d was updated!", accountDTO.getId()));
    } catch (Exception e){
      LOGGER.info(String.format("Error -> %s", e.getMessage()));
    }
  }
}
