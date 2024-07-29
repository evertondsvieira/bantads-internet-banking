package bantads.account_command.service.provider.strategy;

import java.util.Optional;

import bantads.account_command.entity.Account;

public interface TransactionStrategy {
  public void operation(Account originAccount, Account destinationAccount, Double ammount);
  public Account save(Account account);
  public Account getOriginAccount();
  public Optional<Account> getDestinationAccount();
}
