package bantads.account_command.sagashandler.sagaprofileupdate.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;

@Service
public class RabbitMQSagaProfileProducer {

  private String exchange = "saga.profile.update.exchange";

	private String updateAccountResponseRouting = "update.account.response";

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSagaProfileProducer.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public RabbitMQSagaProfileProducer(RabbitTemplate rabbitTemplate){
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(AccountDTO accountDTO) {
      LOGGER.info(String.format("Json message sent -> %s", accountDTO.toString()));
      rabbitTemplate.convertAndSend(exchange, updateAccountResponseRouting, accountDTO);
  }
}
