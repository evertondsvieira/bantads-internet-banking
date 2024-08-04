package bantads.msclient.sagashandler.sagaprofileupdate.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import bantads.msclient.dto.ClientDTO;

@Service
public class RabbitMQProducer {

  private String exchange = "saga.profile.update.exchange";

	private String updateClientResponseRouting = "update.client.response";

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

  @Autowired
  private @Qualifier("jsonRabbitTemplate") RabbitTemplate rabbitTemplate;

  public void RabbitMQJsonProducer(RabbitTemplate rabbitTemplate){
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(ClientDTO clientDTO) {
      LOGGER.info(String.format("Json message sent -> %s", clientDTO.toString()));
      rabbitTemplate.convertAndSend(exchange, updateClientResponseRouting, clientDTO);
  }
}
