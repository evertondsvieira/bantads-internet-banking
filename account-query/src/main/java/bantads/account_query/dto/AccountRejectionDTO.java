package bantads.account_query.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRejectionDTO {
  private Long id;
  private String reason;
  private AccountDTO account;
  private ManagerDTO manager;
  private Date createdAt;
}
