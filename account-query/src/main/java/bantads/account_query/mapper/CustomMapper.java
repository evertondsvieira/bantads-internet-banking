package bantads.account_query.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import bantads.account_query.dto.AccountDTO;
import bantads.account_query.dto.ClientDTO;
import bantads.account_query.dto.ManagerDTO;
import bantads.account_query.entity.Account;

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
    ManagerDTO managerDTO = new ManagerDTO(account.getClientName(), account.getManagerCpf());
    accountDTO.setClient(clientDTO);
    accountDTO.setManager(managerDTO);
    return accountDTO;
  }
}
