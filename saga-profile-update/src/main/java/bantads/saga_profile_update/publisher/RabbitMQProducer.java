package bantads.saga_profile_update.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.saga_profile_update.dto.AccountDTO;
import bantads.saga_profile_update.dto.ClientDTO;

@Service
public class RabbitMQProducer {

  private String exchange = "saga.profile.update.exchange";

	private String updateClientRouting = "update.client";

	private String updateAccountRouting = "update.account";


  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(ClientDTO clientDTO) {
      LOGGER.info(String.format("Json message sent -> %s", clientDTO.toString()));
      rabbitTemplate.convertAndSend(exchange, updateClientRouting, clientDTO);
  }

  public void sendMessage(AccountDTO accountDTO){
    LOGGER.info(String.format("Json message sent -> %s", accountDTO.toString()));
    rabbitTemplate.convertAndSend(exchange, updateAccountRouting, accountDTO);
  }
}
