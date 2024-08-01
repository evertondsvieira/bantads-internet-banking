package bantads.account_command.dto;

import java.time.LocalDateTime;
import java.util.Date;

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
public class TransactionRequestDTO {
  private Long id;
  private Long originAccountId;
  private Long destinationAccountId;
  private TransactionType type;
  private Double ammount;
  private Date createdAt;
}
