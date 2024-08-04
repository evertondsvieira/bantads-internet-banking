package bantads.account_query.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import bantads.account_query.enums.AccountSituation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "q_account")
public class Account {
  @Id
  private Long id;

  @Column(name = "account_limit")
  private Double limit = 0.0;
  
  private Double balance = 0.0;

  private Double salary = 0.0;
  
  @Enumerated(EnumType.STRING)
  private AccountSituation situation = AccountSituation.PENDING;
  
  private String managerName;

  private String managerCpf;

  private String clientName;
  
  @Column(nullable = false, unique = true)
  private String clientCpf;
  
  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;
}
