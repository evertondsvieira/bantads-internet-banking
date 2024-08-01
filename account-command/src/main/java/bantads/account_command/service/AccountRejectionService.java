package bantads.account_command.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountRejectionDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.AccountRejection;
import bantads.account_command.entity.Manager;
import bantads.account_command.enums.AccountSituation;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.mapper.CustomMapper;
import bantads.account_command.repository.AccountRejectionRepository;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.repository.ManagerRepository;

@Service
public class AccountRejectionService {
  @Autowired
  private AccountRejectionRepository accountRejectionRepo;

  @Autowired
  private ManagerRepository managerRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private AccountService accountService;

  @Autowired 
  private ModelMapper mapper;

  @Autowired
  private CustomMapper customMapper;

  public String executeRejection(Long accountId, AccountRejectionDTO accountRejectionDTO){
    System.out.println(accountRejectionDTO.toString());
    AccountRejection accountRejection = mapper.map(accountRejectionDTO, AccountRejection.class);
    Optional<Manager> managerOpt = managerRepo.findByCpf(accountRejectionDTO.getManager().getCpf());
    if(managerOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Manager with cpf %s not found", accountRejectionDTO.getManager().getCpf()));
    }
    Optional<Account> accountOpt = accountRepo.findById(accountId);
    if(accountOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Account with id %d not found", accountId));
    }
    Account account = accountOpt.get();
    account.setSituation(AccountSituation.REJECTED);
    accountRejection.setManager(managerOpt.get());
    accountRejection.setAccount(account);
    accountService.updateAccount(account.getId(),customMapper.map(account));
    accountRejectionRepo.save(accountRejection);
    return String.format("Rejection was processed successfully. Client with cpf %s was REJECTED", account.getClient().getCpf());
  }
}
