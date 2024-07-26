package bantads.account_command.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.dto.ClientDTO;
import bantads.account_command.dto.ManagerDTO;
import bantads.account_command.dto.TransactionDTO;
import bantads.account_command.entity.Account;
import bantads.account_command.entity.Client;
import bantads.account_command.entity.Manager;
import bantads.account_command.entity.Transaction;

public class CustomMapper {
  @Autowired
  private ModelMapper mapper;
  
  public Account map(AccountDTO accountDTO){
    Account account = mapper.map(accountDTO, Account.class);
    if(accountDTO.getManager() != null){
      account.setManager(mapper.map(accountDTO.getManager(), Manager.class));
    }
    if(accountDTO.getClient() != null){
      account.setClient(mapper.map(accountDTO.getClient(), Client.class));
    }
    return account;
  }

  public AccountDTO map(Account account){
    AccountDTO accountDTO = mapper.map(account, AccountDTO.class);
    if(account.getManager() != null){
      accountDTO.setManager(mapper.map(account.getManager(), ManagerDTO.class));
    }
    if(account.getClient() != null){
      accountDTO.setClient(mapper.map(account.getClient(), ClientDTO.class));
    }
    return accountDTO;
  }

  public Transaction map(TransactionDTO transactionDTO){
    Transaction transaction = mapper.map(transactionDTO, Transaction.class);
    transaction.setOriginAccount(mapper.map(transactionDTO.getOriginAccount(), Account.class));
    if(transactionDTO.getDestinationAccount() != null){
      transaction.setDestinationAccount(mapper.map(transactionDTO.getDestinationAccount(), Account.class));
    }
    return transaction;
  }


  public TransactionDTO map(Transaction transaction){
    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
    transactionDTO.setOriginAccount(mapper.map(transaction.getOriginAccount(), AccountDTO.class));
    if(transaction.getDestinationAccount() != null){
      transactionDTO.setDestinationAccount(mapper.map(transaction.getDestinationAccount(), AccountDTO.class));
    }
    return transactionDTO;
  }
}
