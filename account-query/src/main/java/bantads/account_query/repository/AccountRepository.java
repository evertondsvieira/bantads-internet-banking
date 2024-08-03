package bantads.account_query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bantads.account_query.entity.Account;
import bantads.account_query.entity.ManagerAccountReport;

import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long>{
  Optional<Account> findByClientCpf(String clientCpf);
  List<Account> findByManagerCpf(String managerCpf);

  @Query(
    """
    SELECT  
    a.managerName AS name, 
    COUNT(*) AS quantity, 
    SUM(
      CASE
        WHEN a.balance > 0 THEN a.balance	
        ELSE 0
      END
    ) AS positiveBalance,
    SUM(
      CASE
        WHEN a.balance < 0 THEN a.balance	
        ELSE 0
      END
    ) AS negativeBalance
    FROM Account a 
    GROUP BY a.managerName 
    """
  )
  List<ManagerAccountReport> getManagerAccountReports();
}
