package bantads.account_command.dto;

import java.util.Date;


import bantads.account_command.entity.Account;
import bantads.account_command.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO {
  private Long id;
  private Account originAccount;
  private Account destinationAccount;
  private TransactionType type;
  private Double ammount;
  private Date createdAt;
}
