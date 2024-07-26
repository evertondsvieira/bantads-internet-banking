package bantads.saga_register.dto;

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
public class AccountDTO {
  private Long id;
  private Double limit = 0.0;
  private Double balance = 0.0;
  private Double salary = 0.0;
  private AccountSituation situation;
  private ManagerDTO manager;
  private ClientAccountDTO client;
  private Date criatedAt;
}