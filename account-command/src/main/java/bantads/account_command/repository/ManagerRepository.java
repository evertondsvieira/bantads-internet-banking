package bantads.account_command.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bantads.account_command.entity.Manager;
import bantads.account_command.entity.ManagerAccountInfo;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
  
  @Query("SELECT m FROM Manager m where m.cpf = :cpf and (m.deleted = 'N' or m.deleted is NULL)")
  Optional<Manager> findByCpf(@Param("cpf") String cpf);

  @Query("SELECT count(*) FROM Manager m WHERE m.deleted = 'N' or m.deleted is NULL")
  Long getTotalManagers();
  
  @Query(value = 
        """
        SELECT t2.cpf, t2.hasAccount, t2.quantity
        FROM(
          SELECT t.cpf, t.hasAccount, sum(t.quantity) as quantity
          FROM (SELECT 
                  m.cpf, 
                  case when a.id is not null then 'true'
                        else 'false'
                  end as hasAccount, 
                  count(*) as quantity
                FROM t_manager m 
                LEFT JOIN t_account a on a.manager_id = m.id 
                WHERE m.deleted = 'N' or m.deleted is null
                GROUP by m.cpf, a.id) t
          GROUP BY t.cpf, t.hasAccount
          ORDER BY quantity ASC) t2
        LIMIT 1
        """, nativeQuery = true)
  Optional<ManagerAccountInfo> getManagerCpfWithLeastAccounts();

  @Query(value =
        """
        SELECT t2.cpf, t2.hasAccount, t2.quantity
          FROM(
          SELECT t.cpf, t.hasAccount, sum(t.quantity) as quantity
          FROM (SELECT 
                  m.cpf, 
                  case when a.id is not null then 'true'
                        else 'false'
                  end as hasAccount, 
                  count(*) as quantity
                FROM t_manager m 
                LEFT JOIN t_account a on a.manager_id = m.id 
                WHERE m.deleted = 'N' or m.deleted is null
                GROUP by m.cpf, a.id) t
          GROUP BY t.cpf, t.hasAccount
          ORDER BY quantity DESC) t2
        LIMIT 1
        """, nativeQuery = true)
  Optional<ManagerAccountInfo> getManagerCpfWithMoreAccounts();  
}
