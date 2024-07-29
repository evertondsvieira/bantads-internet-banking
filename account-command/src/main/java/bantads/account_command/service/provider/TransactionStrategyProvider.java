package bantads.account_command.service.provider;

import javax.naming.OperationNotSupportedException;

import org.springframework.stereotype.Service;

import bantads.account_command.enums.TransactionType;
import bantads.account_command.repository.AccountRepository;
import bantads.account_command.service.provider.strategy.Deposit;
import bantads.account_command.service.provider.strategy.TransactionStrategy;
import bantads.account_command.service.provider.strategy.Transfer;
import bantads.account_command.service.provider.strategy.Withdrawl;

@Service
public class TransactionStrategyProvider {
  
  private AccountRepository accountRepo;

  public TransactionStrategyProvider(AccountRepository accountRepo){
    this.accountRepo = accountRepo;
  }

  public TransactionStrategy getStrategy(TransactionType type) throws OperationNotSupportedException{
    switch (type) {
      case DEPOSIT:
        return new Deposit(accountRepo);
      case WITHDRAWL:
        return new Withdrawl(accountRepo);
      case TRANSFER:
        return new Transfer(accountRepo);
      default:
        throw new OperationNotSupportedException("Operation type not supoorted");
    }
  }
}
