package bantads.account_query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_query.entity.Account;
import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long>{
  Optional<Account> findByClientCpf(String clientCpf);
  List<Account> findByManagerCpf(String managerCpf);
}
