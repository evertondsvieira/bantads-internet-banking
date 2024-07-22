package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.AccountRejection;

public interface AccountRejectionRepository extends JpaRepository<AccountRejection, Long>{

}
