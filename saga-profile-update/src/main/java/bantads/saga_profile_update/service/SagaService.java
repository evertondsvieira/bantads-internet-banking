package bantads.saga_profile_update.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.saga_profile_update.dto.AccountDTO;
import bantads.saga_profile_update.dto.ClientDTO;
import bantads.saga_profile_update.publisher.RabbitMQProducer;

@Service
public class SagaService {
  
  @Autowired
  private RabbitMQProducer producer;

  public String updateClient(ClientDTO clientDTO){
    producer.sendMessage(clientDTO);
    return "Client update request is being processed";
  }

  public String updateAccount(AccountDTO accountDTO){
    producer.sendMessage(accountDTO);
    return "Account update request is being processed";
  }
}
