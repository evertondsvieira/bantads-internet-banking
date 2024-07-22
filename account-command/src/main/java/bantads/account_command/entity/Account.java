package bantads.account_command.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import bantads.account_command.enums.AccountSituation;
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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_account")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "account_limit")
  private Double limit = 0.0;
  
  private Double balance = 0.0;

  private Double salary = 0.0;

  private AccountSituation situation;

  @ManyToOne
  @JoinColumn(name = "manager_id", nullable = true)
  private Manager manager;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date criatedAt;
}
