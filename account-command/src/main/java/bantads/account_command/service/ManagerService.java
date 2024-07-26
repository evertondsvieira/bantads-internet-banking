package bantads.account_command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.entity.Manager;
import bantads.account_command.repository.ManagerRepository;

@Service
public class ManagerService {
  
  // Remove this when RealService is implemented
  private static Manager mockManager = new Manager(Long.parseLong("100"), "The Cool Manager", "12345678910");
  
  @Autowired
  private ManagerRepository managerRepo;

  public Manager getManagerWithLeastAccounts(){
    return mockManager;
  }
}
