package bantads.account_query.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_query.dto.TransactionDTO;
import bantads.account_query.entity.Transaction;
import bantads.account_query.enums.TransactionType;
import bantads.account_query.mapper.CustomMapper;
import bantads.account_query.repository.TransactionRepository;

@Service
public class TransactionService {
  @Autowired
  private TransactionRepository transactionRepo;

  @Autowired
  private CustomMapper mapper;

  public TransactionDTO createTransaction(TransactionDTO transactionDTO){
    if(transactionDTO.getType() != TransactionType.TRANSFER){
      transactionDTO.setDestinationAccount(null);
    }
    Transaction transaction = mapper.map(transactionDTO);
    transaction = transactionRepo.save(transaction);  
    return mapper.map(transaction);
  }

  public List<TransactionDTO> getTransactionByAccountIdAndFilters(Long accountId, LocalDateTime startDate, LocalDateTime endDate, TransactionType type){
    List<Transaction> transactions = transactionRepo.findTransactionByAccountIdAndFilters(accountId, startDate, endDate, type);
    List<TransactionDTO> transactionDTOs = transactions.stream()
      .map((tr) -> mapper.map(tr))
      .toList();
    return transactionDTOs;
  }
}
