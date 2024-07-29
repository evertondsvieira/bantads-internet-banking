package bantads.account_query.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import bantads.account_query.dto.AccountDTO;
import bantads.account_query.dto.ClientDTO;
import bantads.account_query.dto.ManagerDTO;
import bantads.account_query.dto.TransactionDTO;
import bantads.account_query.entity.Account;
import bantads.account_query.entity.Transaction;

public class CustomMapper {
  @Autowired
  private ModelMapper mapper;

  public Account map(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO, Account.class);
    account.setClientCpf(accountDTO.getClient().getCpf());
    account.setClientName(accountDTO.getClient().getName());
    account.setManagerCpf(accountDTO.getManager().getCpf());
    account.setManagerName(accountDTO.getManager().getName());
    return account;
  }

  public AccountDTO map(Account account){
    AccountDTO accountDTO = mapper.map(account, AccountDTO.class);
    ClientDTO clientDTO = new ClientDTO(account.getClientName(), account.getClientCpf());
    ManagerDTO managerDTO = new ManagerDTO(account.getManagerName(), account.getManagerCpf());
    accountDTO.setClient(clientDTO);
    accountDTO.setManager(managerDTO);
    return accountDTO;
  }

  public TransactionDTO map(Transaction transaction){
    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
    AccountDTO originAccountDTO = new AccountDTO();
    originAccountDTO.setId(transaction.getOriginAccountId());
    originAccountDTO.setBalance(transaction.getCurrentBalance());
    originAccountDTO.setClient(new ClientDTO(transaction.getOriginAccountClientName(), transaction.getOriginAccountClientCpf()));
    transactionDTO.setOriginAccount(originAccountDTO);
    if(transaction.getDestinationAccountId() != null){
      AccountDTO destinationAccount = new AccountDTO();
      destinationAccount.setId(transaction.getDestinationAccountId());
      destinationAccount.setClient(new ClientDTO(transaction.getDestinationAccountClientName(), transaction.getDestinationAccountClientCpf()));
      transactionDTO.setDestinationAccount(destinationAccount);
    }
    return transactionDTO;
  }

  public Transaction map(TransactionDTO transactionDTO){
    Transaction transaction = mapper.map(transactionDTO, Transaction.class);
    AccountDTO originAcocunt = transactionDTO.getOriginAccount();
    transaction.setOriginAccountId(originAcocunt.getId());
    transaction.setOriginAccountClientName(originAcocunt.getClient().getName());
    transaction.setOriginAccountClientCpf(originAcocunt.getClient().getCpf());
    transaction.setCurrentBalance(originAcocunt.getBalance());
    if(transactionDTO.getDestinationAccount() != null){
      AccountDTO destinationAcocunt = transactionDTO.getDestinationAccount();
      transaction.setDestinationAccountId(destinationAcocunt.getId());
      transaction.setDestinationAccountClientName(destinationAcocunt.getClient().getName());
      transaction.setDestinationAccountClientCpf(destinationAcocunt.getClient().getCpf());
    }
    return transaction;
  }
}
