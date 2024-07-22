package bantads.account_command.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.Client;
import bantads.account_command.entity.Manager;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.repository.ClientRepository;
import bantads.account_command.repository.ManagerRepository;

@Service
public class AccountService {
  
  @Autowired
  private ModelMapper mapper;

  @Autowired
  private ClientRepository clientRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private ManagerRepository managerRepo;

  public AccountDTO createAccount(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO, Account.class);
    // TODO create method to get manager with least clients
    // Manager manager = mapper.map(accountDTO.getManager(), Manager.class);
    Optional<Client> clientOpt= clientRepo.findByCpf(accountDTO.getClient().getCpf());
    Client client;
    if(clientOpt.isEmpty()){
      client = mapper.map(accountDTO.getClient(), Client.class);
    } else {
      client = clientOpt.get();
    }
    account.setClient(client);
    account.setLimit(this.getAppropriateLimit(account));
    clientRepo.save(client);
    account = accountRepo.save(account);
    return mapper.map(account, AccountDTO.class);
  }

  private Double getAppropriateLimit(Account account){
    if(account.getSalary() < 2000) return 0.0;
    return account.getSalary()/2;
  }
}
