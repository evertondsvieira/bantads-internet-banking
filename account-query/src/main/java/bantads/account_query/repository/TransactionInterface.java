package bantads.account_query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_query.entity.Transaction;

public interface TransactionInterface extends JpaRepository<Transaction, Long>{

}
