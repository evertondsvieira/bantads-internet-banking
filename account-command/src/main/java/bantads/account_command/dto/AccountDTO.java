package bantads.account_command.dto;

import java.io.Serializable;
import java.util.Date;

import bantads.account_command.enums.AccountSituation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDTO implements Serializable{
  private Long id;
  private Double limit = 0.0;
  private Double balance = 0.0;
  private Double salary = 0.0;
  private AccountSituation situation = AccountSituation.PENDING;
  private ManagerDTO manager;
  private ClientDTO client;
  private Date createdAt;
}
