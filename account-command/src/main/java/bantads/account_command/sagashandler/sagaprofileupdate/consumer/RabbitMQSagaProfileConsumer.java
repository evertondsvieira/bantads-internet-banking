package bantads.account_command.sagashandler.sagaprofileupdate.consumer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.sagashandler.sagaprofileupdate.publisher.RabbitMQSagaProfileProducer;
import bantads.account_command.service.AccountService;

@Service
public class RabbitMQSagaProfileConsumer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSagaProfileConsumer.class);

  @Autowired
  private AccountService accountService;

  @Autowired
  private RabbitMQSagaProfileProducer producer;

  @Autowired
  private AccountRepository accountRepo;

  @RabbitListener(queues = "update.account.queue")
  public void handleClientUpdateQueue(AccountDTO accountDTO){
    LOGGER.info(String.format("Received message -> %s", accountDTO.toString()));
    try{
      Optional<Account> accountOpt = accountRepo.findAccountByClientCpf(accountDTO.getClient().getCpf());
      if(accountOpt.isEmpty()){
        throw new RecordNotFoundException(String.format("Account of client with CPF %s was not found", accountDTO.getClient().getCpf()));
      }
      AccountDTO updatedAccountDTO = accountService.updateAccount(accountOpt.get().getId(), accountDTO);
      LOGGER.info(String.format("Account Was updated succefully -> %s", accountDTO.toString()));
      producer.sendMessage(updatedAccountDTO);
    } catch (Exception e){
      LOGGER.info(String.format("Error -> %s", e.getMessage()));
    }
  }

}
