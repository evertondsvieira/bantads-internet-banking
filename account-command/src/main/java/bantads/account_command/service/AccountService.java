package bantads.account_command.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.Client;
import bantads.account_command.entity.ManagerAccountInfo;
import bantads.account_command.entity.Manager;
import bantads.account_command.entity.timeline.AccountTimeline;
import bantads.account_command.enums.AccountSituation;
import bantads.account_command.enums.Event;
import bantads.account_command.exceptions.RecordDuplicationException;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.mapper.CustomMapper;
import bantads.account_command.publisher.RabbitMQProducer;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.repository.AccountTimelineRepository;
import bantads.account_command.repository.ClientRepository;
import bantads.account_command.repository.ManagerRepository;

@Service
public class AccountService {
  
  @Autowired
  private CustomMapper mapper;

  @Autowired 
  private ModelMapper modelMapper;

  @Autowired
  private ClientRepository clientRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private AccountTimelineRepository accountTimelineRepo;

  @Autowired
  private RabbitMQProducer producer;

  @Autowired
  private ManagerRepository managerRepo;

  public AccountDTO createAccount(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO);
    Optional<Client> clientOpt= clientRepo.findByCpf(accountDTO.getClient().getCpf());
    Client client;
    if(clientOpt.isEmpty()){
      client = account.getClient();
    } else {
      client = clientOpt.get();
    }
    if(accountRepo.findAccountByClientCpf(client.getCpf()).isPresent()){
      throw new RecordDuplicationException(String.format("The client with cpf %s already has an account", client.getCpf()));
    }
    Manager manager = getManagerWithLeastAccounts();
    account.setClient(client);
    account.setManager(manager);
    account.setSituation(AccountSituation.PENDING);
    account.setBalance(0.0);
    account.setLimit(this.getAppropriateLimit(account));
    managerRepo.save(manager);
    clientRepo.save(client);
    account = accountRepo.save(account);
    saveAccountTimeline(Event.CREATE_ACCOUNT, account);
    AccountDTO accountDTOCreated = mapper.map(account);
    producer.sendMessage(accountDTOCreated, Event.CREATE_ACCOUNT);
    return accountDTOCreated;
  }

  public AccountDTO updateAccount(Long id, AccountDTO accountDTO){
    Optional<Account> accountOpt = accountRepo.findById(id);
    if(accountOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Account with id %d not found", id));
    }
    Account accountUpdate = mapper.map(accountDTO);
    Account account = accountOpt.get();
    if(accountUpdate.getSalary() != null){
      account.setSalary(accountUpdate.getSalary());
      account.setLimit(getAppropriateLimit(accountUpdate));
    }
    if(accountUpdate.getSituation() != null){
      account.setSituation(accountUpdate.getSituation());
    }
    if(accountUpdate.getManager() != null){
      Optional<Manager> managerOpt = managerRepo.findByCpf(accountUpdate.getManager().getCpf());
      if(managerOpt.isEmpty()){
        throw new RecordNotFoundException(String.format("Manager with cpf %s not found", accountUpdate.getManager().getCpf()));
      }
      account.setManager(managerOpt.get());
    }
    account = accountRepo.save(account);
    saveAccountTimeline(Event.UPDATE_ACCOUNT, account);
    AccountDTO accountDTOUpdated = mapper.map(account);
    producer.sendMessage(accountDTOUpdated, Event.UPDATE_ACCOUNT);
    return accountDTOUpdated;
  }

  private Double getAppropriateLimit(Account account){
    if(account.getSalary() < 2000) return 0.0;
    return account.getSalary()/2;
  }

  private void saveAccountTimeline(Event event, Account account){
    AccountTimeline accountTimeline = modelMapper.map(account, AccountTimeline.class);
    accountTimeline.setAccountEvent(event);
    accountTimelineRepo.saveAndFlush(accountTimeline);
  }

  private Manager getManagerWithLeastAccounts(){
    Optional<ManagerAccountInfo> ManagerAccountInfoOpt = managerRepo.getManagerCpfWithLeastAccounts();
    if(ManagerAccountInfoOpt.isEmpty()){
      throw new IllegalArgumentException(String.format("No manager present to take this account"));
    }
    ManagerAccountInfo managerAccountInfo = ManagerAccountInfoOpt.get();
    Optional<Manager> managerOpt = managerRepo.findByCpf(managerAccountInfo.getCpf());
    Manager manager = managerOpt.get();
    return manager;
  }
}
