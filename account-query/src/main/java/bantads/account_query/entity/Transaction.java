package bantads.account_query.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import bantads.account_query.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "t_transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long originAccountId;
  private String originAccountClientName;
  private String originAccountClientCpf;
  private Long destinationAccountId;
  private String destinationAccountClientName;
  private String destinationAccountClientCpf;
  private Double currentBalance;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;
  
  private Double ammount;
  
  @Column(name = "time", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;
}
