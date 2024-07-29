package bantads.account_command.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.TransactionDTO;
import bantads.account_command.dto.TransactionRequestDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.Transaction;
import bantads.account_command.enums.Event;
import bantads.account_command.mapper.CustomMapper;
import bantads.account_command.publisher.RabbitMQProducer;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.repository.TransactionRepository;
import bantads.account_command.service.provider.TransactionStrategyProvider;
import bantads.account_command.service.provider.strategy.TransactionStrategy;

@Service
public class TransactionService {
    @Autowired
  private CustomMapper mapper;

  @Autowired 
  private ModelMapper modelMapper;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private AccountService accountService;

  @Autowired 
  private TransactionRepository transactionRepo;

  @Autowired
  private RabbitMQProducer producer;

  @Autowired
  private TransactionStrategyProvider strategyProvider;

  public TransactionDTO executeTransaction(TransactionRequestDTO transactionRequest){
    Optional<Account> originAccountOpt = accountRepo.findById(transactionRequest.getOriginAccountId());
    Optional<Account> destinationAccountOpt = transactionRequest.getDestinationAccountId() != null ?
      accountRepo.findById(transactionRequest.getDestinationAccountId()) :
      Optional.empty();
    Account originAccount = originAccountOpt.isPresent() ? originAccountOpt.get() : null;
    Account destinationAccount = destinationAccountOpt.isPresent() ? destinationAccountOpt.get() : null;
    try{
      TransactionStrategy strategy = strategyProvider.getStrategy(transactionRequest.getType());
      strategy.operation(originAccount, destinationAccount, transactionRequest.getAmmount());
      originAccount = strategy.getOriginAccount();
      destinationAccountOpt = strategy.getDestinationAccount();
      Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
      transaction.setOriginAccount(originAccount);
      if(destinationAccountOpt.isPresent()){
        transaction.setDestinationAccount(destinationAccountOpt.get());
      }
      transactionRepo.save(transaction);
      producer.sendMessage(mapper.map(originAccount),Event.UPDATE_ACCOUNT);
      accountService.saveAccountTimeline(Event.UPDATE_ACCOUNT, originAccount);
      if(destinationAccountOpt.isPresent()){
        producer.sendMessage(mapper.map(destinationAccountOpt.get()), Event.UPDATE_ACCOUNT);
        accountService.saveAccountTimeline(Event.UPDATE_ACCOUNT, destinationAccountOpt.get());
      }
      TransactionDTO transactionDTO = mapper.map(transaction);
      producer.sendMessage(transactionDTO,Event.TRANSACTION);
      return transactionDTO;
    } 
    catch (Exception e){
      throw new IllegalArgumentException(e);  
    }
  }
}
