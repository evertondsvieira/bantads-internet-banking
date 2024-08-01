package bantads.account_query.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_query.dto.TransactionDTO;
import bantads.account_query.enums.TransactionType;
import bantads.account_query.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
  
  @Autowired
  private TransactionService transactionService;

  @GetMapping("/account/{id}")
  public ResponseEntity<List<TransactionDTO>> getTransactionByAccountIdAndFilters(
    @PathVariable("id") Long accountId,  
    @RequestParam(value = "startDate", required = false) String start,
    @RequestParam(value = "endDate", required = false) String end,
    @RequestParam(value = "type", required = false) TransactionType type
  ){
    try {
      DateTimeFormatter dateTimeFormat = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_DATE_TIME).toFormatter();
      LocalDateTime startDate = start != null ? dateTimeFormat.parse(start, LocalDateTime::from) : null;;
      LocalDateTime endDate = end != null ? dateTimeFormat.parse(end, LocalDateTime::from) : null;
      List<TransactionDTO> transactionDTOs = transactionService.getTransactionByAccountIdAndFilters(accountId, startDate, endDate, type);
      return new ResponseEntity<>(transactionDTOs, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
  }
}
