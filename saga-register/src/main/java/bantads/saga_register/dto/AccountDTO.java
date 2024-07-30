package bantads.saga_register.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import bantads.saga_register.dto.enums.AccountSituation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDTO implements Serializable{
  private Long id;
  private Double limit;
  private Double balance;
  private Double salary;
  private AccountSituation situation;
  private ManagerDTO manager;
  private ClientAccountDTO client;
  private Date createdAt;
}