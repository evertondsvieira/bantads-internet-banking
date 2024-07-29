package bantads.account_query.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_query.dto.TransactionDTO;
import bantads.account_query.service.TransactionService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
  
  @Autowired
  private TransactionService transactionService;

  @GetMapping("/account/{id}")
  public ResponseEntity<List<TransactionDTO>> getTransactionByAccountIdAndBetweenPeriod(
    @PathParam("id") Long accountId,  
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate 
  ){
    try {
      // Optional<Date> startDateOpt;
      // Optional<Date> endDateOpt;
      // if(startDate != null){
      //   startDateOpt = Optional.of(startDate);
      // } else {
      //   startDateOpt = Optional.empty();
      // }

      // if(endDate != null){
      //   endDateOpt = Optional.of(endDate);
      // } else {
      //   endDateOpt = Optional.empty();
      // }
      List<TransactionDTO> transactionDTOs = transactionService.getTransactionByAccountIdAndBetweenPeriod(accountId, startDate, endDate);
      return new ResponseEntity<>(transactionDTOs, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
  }
}
