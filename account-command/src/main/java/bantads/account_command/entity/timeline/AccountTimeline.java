package bantads.account_command.entity.timeline;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import bantads.account_command.entity.Client;
import bantads.account_command.entity.Manager;
import bantads.account_command.enums.AccountSituation;
import bantads.account_command.enums.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "t_account_timeline")
public class AccountTimeline  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long timelineId;

  @Enumerated(EnumType.STRING)
  Event accountEvent;

  @Column(name = "account_limit")
  private Double limit = 0.0;
  
  private Double balance = 0.0;

  private Double salary = 0.0;

  private AccountSituation situation = AccountSituation.PENDING;

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
