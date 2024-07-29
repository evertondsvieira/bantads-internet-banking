package bantads.account_command.service.provider.strategy;

import java.util.Optional;

import bantads.account_command.entity.Account;
import bantads.account_command.exceptions.OperationBlockedByBusinessRule;
import bantads.account_command.repository.AccountRepository;

public class Transfer implements TransactionStrategy{
  
  private AccountRepository accountRepo;

  private Account originAccount;
  
  private Account destinationAccount;

  public Transfer(AccountRepository accountRepo){
    this.accountRepo = accountRepo;
  }

  public void operation(Account originAccount, Account destinationAccount, Double ammount){
    if(originAccount == null){
      throw new OperationBlockedByBusinessRule("Origin Account must be informed");
    }
    if(destinationAccount == null){
      throw new OperationBlockedByBusinessRule("Origin Destination must be informed");
    }

    originAccount.withdrawl(ammount);
    destinationAccount.deposit(ammount);
    this.originAccount = save(originAccount);
    this.destinationAccount = save(destinationAccount);
  }

  public Account save(Account account){
    return accountRepo.save(account);
  }

  public Account getOriginAccount(){
    return this.originAccount;
  }

  public Optional<Account> getDestinationAccount(){
    return Optional.of(this.destinationAccount);
  }
}
