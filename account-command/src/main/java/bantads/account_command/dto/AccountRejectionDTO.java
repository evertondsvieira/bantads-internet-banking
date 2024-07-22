package bantads.account_command.dto;

import java.util.Date;

import bantads.account_command.entity.Account;
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
  private Account account;
  private ManagerDTO manager;
  private Date createdAt;
}
