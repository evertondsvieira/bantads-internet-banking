package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
