package bantads.account_query.repository;

import java.time.LocalDateTime;
import java.util.List;
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
    AND (:type IS NULL OR t.transactionType = :type)
    AND (t.createdAt BETWEEN :initial_date AND :end_date 
         OR (cast(:end_date AS DATE) IS NULL AND t.createdAt >= :initial_date)
         OR (cast(:initial_date AS DATE) IS NULL and t.createdAt <= :end_date)
         OR (cast(:initial_date AS DATE) IS NULL and cast(:end_date AS DATE) IS NULL))
    
    """
  )
  List<Transaction> findTransactionByAccountIdAndFilters(
    @Param("account_id") Long accountId,
    @Param("initial_date") LocalDateTime initialDate,
    @Param("end_date") LocalDateTime endDate,
    @Param("type") TransactionType type
  );

  List<Transaction> findByOriginAccountId(Long originAccountId);

  List<Transaction> findByOriginAccountIdAndTransactionType(Long originAccountId, TransactionType transactionType);
}