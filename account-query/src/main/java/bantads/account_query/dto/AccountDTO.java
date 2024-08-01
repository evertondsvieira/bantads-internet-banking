package bantads.account_query.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bantads.account_query.enums.AccountSituation;
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
@JsonInclude(Include.NON_NULL)
public class AccountDTO implements Serializable{
  private Long id;
  private Double limit = 0.0;
  private Double balance = 0.0;
  private Double salary = 0.0;
  private AccountSituation situation;
  private ManagerDTO manager;
  private ClientDTO client;
  private Date createdAt;
}
