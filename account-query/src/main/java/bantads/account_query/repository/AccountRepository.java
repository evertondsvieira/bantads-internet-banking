package bantads.account_query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_query.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
