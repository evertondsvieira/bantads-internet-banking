package bantads.account_command.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import bantads.account_command.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "t_transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "origin_account_id", nullable = false)
  private Account originAccount;

  @ManyToOne
  @JoinColumn(name = "destination_account_id", nullable = true)
  private Account destinationAccount;

  @Column(nullable = false)
  private TransactionType type;

  @Column(name = "ammount", nullable = false)
  private Double ammount;
  
  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;
}
