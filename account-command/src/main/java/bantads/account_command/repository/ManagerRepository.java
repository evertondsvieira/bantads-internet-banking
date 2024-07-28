package bantads.account_command.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bantads.account_command.entity.Manager;
import bantads.account_command.entity.ManagerAccountInfo;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
  
  Optional<Manager> findByCpf(String cpf);

  @Query("SELECT count(*) FROM Manager m")
  Long getTotalManagers();
  
  @Query(value = 
        """
         SELECT t.cpf, t.hasAccount, t.quantity
         FROM (SELECT 
                 m.cpf, 
                 case when a.id is not null then 'true'
                      else 'false'
                 end as hasAccount, 
                 count(*) as quantity
               FROM t_manager m 
               LEFT JOIN t_account a on a.manager_id = m.id 
               GROUP by m.cpf, a.id
               ORDER by quantity DESC, a.id NULLS FIRST) t
         LIMIT 1
         """, nativeQuery = true)
  Optional<ManagerAccountInfo> getManagerCpfWithLeastAccounts();

  @Query(value =
         """
         SELECT t.cpf, t.hasAccount, t.quantity
         FROM (SELECT 
                 m.cpf, 
                 case when a.id is not null then 'true'
                      else 'false'
                 end as hasAccount,
                 count(*) as quantity
               FROM t_manager m 
               LEFT JOIN t_account a on a.manager_id = m.id 
               GROUP by m.cpf, a.id
               ORDER by quantity ASC, a.id NULLS LAST) t
         LIMIT 1
         """, nativeQuery = true)
  Optional<ManagerAccountInfo> getManagerCpfWithMoreAccounts();  
}
