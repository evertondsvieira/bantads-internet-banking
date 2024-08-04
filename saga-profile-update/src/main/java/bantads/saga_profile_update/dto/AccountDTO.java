package bantads.saga_profile_update.dto;

import java.io.Serializable;
import java.util.Date;

import bantads.saga_profile_update.enums.AccountSituation;
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
  private Double limit;
  private Double balance;
  private Double salary;
  private String situation;
  private ManagerDTO manager;
  private ClientDTO client;
  private Date createdAt;
}