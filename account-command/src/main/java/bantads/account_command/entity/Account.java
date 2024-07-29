package bantads.account_command.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import bantads.account_command.enums.AccountSituation;
import bantads.account_command.exceptions.OperationBlockedByBusinessRule;
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

  @Enumerated(EnumType.STRING)
  private AccountSituation situation = AccountSituation.PENDING;

  @ManyToOne
  @JoinColumn(name = "manager_id", nullable = true)
  private Manager manager;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false, unique = true)
  private Client client;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  public Double deposit(Double ammount){
    if(ammount < 0){
      throw new OperationBlockedByBusinessRule(
        String.format(
          "Can't deposit a negative value (%.2f)", ammount
        ));
    }
    if(ammount == 0){
      throw new OperationBlockedByBusinessRule(
        String.format("Ammount informed is zero"));
    }
    return this.balance += ammount;
  }

  public Double withdrawl(Double ammount){
    if(ammount < 0){
      throw new OperationBlockedByBusinessRule(
        String.format(
          "Can't withdraw a negative value (%.2f)", ammount
        ));
    }
    if(ammount == 0){
      throw new OperationBlockedByBusinessRule(
        String.format("Ammount informed is zero"));
    }
    if(ammount > this.balance + this.limit){
      throw new OperationBlockedByBusinessRule(
        String.format(
          "Ammount of %2.f is higher than balance + limit = %.2f + %.2f", 
          ammount,
          this.balance,
          this.limit
        ));
    }
    return this.balance -= ammount;
  }
}
