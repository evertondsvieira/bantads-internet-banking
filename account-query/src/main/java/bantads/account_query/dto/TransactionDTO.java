package bantads.account_query.dto;

import java.util.Date;


import bantads.account_query.enums.TransactionType;
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
  private AccountDTO originAccount;
  private AccountDTO destinationAccount;
  private TransactionType type;
  private Double ammount;
  private Date createdAt;
}
