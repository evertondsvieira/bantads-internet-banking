package bantads.account_command.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.Client;
import bantads.account_command.entity.Manager;
import bantads.account_command.mapper.CustomMapper;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.repository.ClientRepository;
import bantads.account_command.repository.ManagerRepository;

@Service
public class AccountService {
  
  @Autowired
  private CustomMapper mapper;

  @Autowired
  private ClientRepository clientRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private ManagerService managerService;

  @Autowired
  private ManagerRepository managerRepo;

  public AccountDTO createAccount(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO);
    // TODO create method to get manager with least clients
    Manager manager = managerService.getManagerWithLeastAccounts();
    Optional<Client> clientOpt= clientRepo.findByCpf(accountDTO.getClient().getCpf());
    Client client;
    if(clientOpt.isEmpty()){
      client = account.getClient();
    } else {
      client = clientOpt.get();
    }
    account.setClient(client);
    account.setManager(manager);
    account.setLimit(this.getAppropriateLimit(account));
    managerRepo.save(manager);
    clientRepo.save(client);
    account = accountRepo.save(account);
    
    return mapper.map(account);
  }

  public AccountDTO updateAccount(Long id, AccountDTO accountDTO){
    Optional<Account> accountOpt = accountRepo.findById(id);
    if(accountOpt.isEmpty()){
      throw new IllegalArgumentException(String.format("Account with id %d not found", id));
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
        throw new IllegalArgumentException(String.format("Manager with cpf %s not found", accountUpdate.getManager().getCpf()));
      }
      account.setManager(managerOpt.get());
    }
    account = accountRepo.save(account);
    return mapper.map(account);
  }

  private Double getAppropriateLimit(Account account){
    if(account.getSalary() < 2000) return 0.0;
    return account.getSalary()/2;
  }
}
