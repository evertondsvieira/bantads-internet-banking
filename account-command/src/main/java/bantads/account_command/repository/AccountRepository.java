package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bantads.account_command.entity.Account;
import bantads.account_command.entity.Manager;

import java.util.Optional;
import java.util.List;



public interface AccountRepository extends JpaRepository<Account, Long>{
  @Query(value = 
        """
         SELECT a
         FROM Account a
         WHERE a.client.cpf = :client_cpf
         """)
  Optional<Account> findAccountByClientCpf(@Param("client_cpf") String clientCpf);

  List<Account> findByManager(Manager manager);
}
