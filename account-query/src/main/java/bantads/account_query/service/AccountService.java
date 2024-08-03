package bantads.account_query.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_query.dto.AccountDTO;
import bantads.account_query.entity.Account;
import bantads.account_query.entity.ManagerAccountReport;
import bantads.account_query.exceptions.RecordNotFoundException;
import bantads.account_query.mapper.CustomMapper;
import bantads.account_query.repository.AccountRepository;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private CustomMapper mapper;

  public AccountDTO createAccount(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO);
    account = accountRepo.save(account);
    return mapper.map(account);
  }

  public AccountDTO getAccountById(Long id){
    Optional<Account> accountOptional = accountRepo.findById(id);
    if (accountOptional.isEmpty()){
      throw new RecordNotFoundException(String.format("Account with id %d not found", id));
    }
    Account account = accountOptional.get();
    System.out.println(account.toString());
    System.out.println(mapper.map(account).toString());
    return mapper.map(account);
  }

  public List<AccountDTO> getAccounts(){
    List<Account> accounts = accountRepo.findAll();
    List<AccountDTO> accountDTOs = accounts.stream()
      .map((ac) -> mapper.map(ac))
      .toList();

    if(accountDTOs.size() == 0){
      throw new RecordNotFoundException("There is no accounts");
    }
    return accountDTOs;
  }

  public List<AccountDTO> getAccountsByManagerCpf(String cpf){
    List<Account> accounts = accountRepo.findByManagerCpf(cpf);
    List<AccountDTO> accountDTOs = accounts.stream()
      .map((ac) -> mapper.map(ac))
      .toList();
    if(accountDTOs.size() == 0){
      throw new RecordNotFoundException("There is no accounts");
    }
    return accountDTOs;
  }


  public AccountDTO getAccountByClientCpf(String cpf){
    Optional<Account> accountOpt = accountRepo.findByClientCpf(cpf);
    if (accountOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Account with cpf %s not found", cpf));
    }
    return mapper.map(accountOpt.get());
  }

  public AccountDTO updateAccount(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO);
    account = accountRepo.save(account);
    return mapper.map(account);
  }

  public List<ManagerAccountReport> getManagerAccountReports(){
    return accountRepo.getManagerAccountReports();
  }
}
