package bantads.account_query.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bantads.account_query.entity.Transaction;
import bantads.account_query.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
  @Query(
    """
    SELECT t
    FROM Transaction t
    WHERE t.originAccountId = :account_id 
    AND t.time BETWEEN :initial_date AND :end_date
    """
  )
  List<Transaction> findTransactionByAccountIdAndBetweenPeriod(
    @Param("account_id") Long accountId,
    @Param("initial_date") LocalDateTime initialDate,
    @Param("end_date") LocalDateTime endDate
  );

  List<Transaction> findByOriginAccountId(Long originAccountId);

  List<Transaction> findByOriginAccountIdAndTransactionType(Long originAccountId, TransactionType transactionType);
}