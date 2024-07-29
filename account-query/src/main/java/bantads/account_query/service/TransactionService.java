package bantads.account_query.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_query.dto.TransactionDTO;
import bantads.account_query.entity.Transaction;
import bantads.account_query.mapper.CustomMapper;
import bantads.account_query.repository.TransactionRepository;

@Service
public class TransactionService {
  @Autowired
  private TransactionRepository transactionRepo;

  @Autowired
  private CustomMapper mapper;

  public TransactionDTO createTransaction(TransactionDTO transactionDTO){
    Transaction transaction = mapper.map(transactionDTO);
    transaction = transactionRepo.save(transaction);  
    return mapper.map(transaction);
  }

  public List<TransactionDTO> getTransactionByAccountIdAndBetweenPeriod(Long accountId, LocalDateTime startDate, LocalDateTime endDate){
    List<Transaction> transactions = transactionRepo.findTransactionByAccountIdAndBetweenPeriod(accountId, startDate, endDate);
    List<TransactionDTO> transactionDTOs = transactions.stream()
      .map((tr) -> mapper.map(tr))
      .toList();
    return transactionDTOs;
  }
}
