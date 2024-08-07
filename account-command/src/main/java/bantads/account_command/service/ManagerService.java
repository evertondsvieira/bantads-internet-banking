package bantads.account_command.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.dto.ManagerDTO;
import bantads.account_command.entity.ManagerAccountInfo;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.Manager;
import bantads.account_command.exceptions.RecordCannotBeDeleted;
import bantads.account_command.exceptions.RecordDuplicationException;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.mapper.CustomMapper;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.repository.ManagerRepository;

@Service
public class ManagerService {
  
  // Remove this when RealService is implemented
  private static Manager mockManager = new Manager(Long.parseLong("100"), "The Cool Manager", "12345678910", 'N');
  
  @Autowired
  private ModelMapper mapper;

  @Autowired
  private CustomMapper customMapper;

  @Autowired
  private ManagerRepository managerRepo;

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private AccountService accountService;

  public Manager getManagerWithLeastAccounts(){
    return mockManager;
  }

  public ManagerDTO createManager(ManagerDTO managerDTO){
    Optional<Manager> managerOpt = managerRepo.findByCpf(managerDTO.getCpf());
    Manager manager;
    if(managerOpt.isPresent()){
      throw new RecordDuplicationException(String.format("Manager With cpf %s already exists", managerDTO.getCpf()));
    }

    /**Manager doestn't exist, so we should just created*/
    manager = mapper.map(managerDTO, Manager.class);
    manager = managerRepo.save(manager);

    Optional<ManagerAccountInfo> managerAccountInfoOpt = managerRepo.getManagerCpfWithMoreAccounts();

    /**The only case so far where the new manager get an account:
     * Manager with more accounts has more than one account
    */
    ManagerAccountInfo managerAccountInfo = managerAccountInfoOpt.get();
    System.out.println(managerAccountInfo.getCpf());
    System.out.println(managerAccountInfo.getHasAccount());
    System.out.println(managerAccountInfo.getQuantity());
    if (managerAccountInfo.getHasAccount() && managerAccountInfo.getQuantity() > 1) {
      Manager managerWithMoreAccounts = managerRepo.findByCpf(managerAccountInfo.getCpf()).get();
      List<Account> accounts = accountRepo.findByManager(managerWithMoreAccounts);
      Account account = accounts.get(0);
      AccountDTO accountDTO = customMapper.map(account);
      accountDTO.setManager(mapper.map(manager, ManagerDTO.class));
      accountDTO = accountService.updateAccount(account.getId(), accountDTO);
    }

    return mapper.map(manager, ManagerDTO.class);
  }

  public ManagerDTO updateManager(String cpf, ManagerDTO managerDTO){
    Optional<Manager> managerOpt = managerRepo.findByCpf(cpf);
    Manager manager;
    if(managerOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Manager with cpf %s not found", cpf));
    }
    manager = managerOpt.get();
    manager.setName(managerDTO.getName());
    manager = managerRepo.save(manager);
    Manager managerToUpdate = manager;
    List<Account> accounts = accountRepo.findByManager(manager);
    accounts.stream()
      .forEach((acc) -> {
        acc.setManager(managerToUpdate);
        AccountDTO accountDTO = customMapper.map(acc);
        accountService.updateAccount(acc.getId(), accountDTO);
      });
    return mapper.map(manager, ManagerDTO.class);
  }

  public void deleteManager(String cpf){
    Optional<Manager> managerOpt = managerRepo.findByCpf(cpf);
    if(managerOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Manager with cpf %s not found", cpf));
    }
    
    /* The last manager cannot be deleted*/
    Long managerQuantity = managerRepo.getTotalManagers();
    if(managerQuantity == 1){
      throw new RecordCannotBeDeleted(String.format("The last manager on the database cannot be deleted (cpf: %s)", cpf));
    }
    Manager manager = managerOpt.get();

    /*When manager is going to be deleted
      his accounts should go to manager with the less accounts 
    */
    Optional<ManagerAccountInfo> managerAccountInfoOpt = managerRepo.getManagerCpfWithLeastAccounts();
    ManagerAccountInfo managerAccountInfo = managerAccountInfoOpt.get();
    Manager managerWithLessAccounts = managerRepo.findByCpf(managerAccountInfo.getCpf()).get();
    List<Account> accounts = accountRepo.findByManager(manager);
    accounts.stream()
      .forEach((acc) -> {
        acc.setManager(managerWithLessAccounts);
        AccountDTO accountDTO = customMapper.map(acc);
        accountService.updateAccount(acc.getId(), accountDTO);
      });
    manager.setDeleted('S');
    managerRepo.save(manager);
  }

  public ManagerDTO getManagerByCpf(String cpf){
    Optional<Manager> managerOpt = managerRepo.findByCpf(cpf);
    if(managerOpt.isEmpty()){
      throw new RecordNotFoundException(String.format("Manager with cpf %s not found", cpf));
    }
    return mapper.map(managerOpt.get(),ManagerDTO.class);
  }  

  public List<ManagerDTO> getManagers(){
    List<Manager> managers = managerRepo.findAll();
    List<ManagerDTO> managersDTOs = managers.stream()
      .map((m) -> mapper.map(m, ManagerDTO.class))
      .toList();
    if(managersDTOs.size() == 0){
      throw new RecordNotFoundException("There are no managers");
    }
    return managersDTOs;
  }
}
