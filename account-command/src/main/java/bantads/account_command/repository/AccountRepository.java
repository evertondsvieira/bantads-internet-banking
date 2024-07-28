package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.Account;
import bantads.account_command.entity.Client;
import bantads.account_command.entity.Manager;

import java.util.Optional;
import java.util.List;



public interface AccountRepository extends JpaRepository<Account, Long>{
  Optional<Account> findByClient(Client client);

  List<Account> findByManager(Manager manager);
}
