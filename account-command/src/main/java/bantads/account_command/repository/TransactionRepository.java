package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
