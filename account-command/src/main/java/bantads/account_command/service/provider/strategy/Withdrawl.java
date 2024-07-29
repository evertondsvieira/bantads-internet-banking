package bantads.account_command.service.provider.strategy;

import java.util.Optional;

import org.springframework.stereotype.Service;

import bantads.account_command.entity.Account;
import bantads.account_command.exceptions.OperationBlockedByBusinessRule;
import bantads.account_command.repository.AccountRepository;

@Service
public class Withdrawl implements TransactionStrategy{
  private AccountRepository accountRepo;
  private Account originAccount;
  public Withdrawl(AccountRepository accountRepo){
    this.accountRepo = accountRepo;
  }

  public void operation(Account originAccount, Account destinationAccount, Double ammount){
    if(originAccount == null){
      throw new OperationBlockedByBusinessRule("Origin Account must be informed");
    }
    originAccount.withdrawl(ammount);
    this.originAccount = save(originAccount);
  }

  public Account save(Account account){
    return accountRepo.save(account);
  }

  public Account getOriginAccount(){
    return this.originAccount;
  }

  public Optional<Account> getDestinationAccount(){
    return Optional.empty();
  }
}
