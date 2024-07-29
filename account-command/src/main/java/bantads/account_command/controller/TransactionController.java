package bantads.account_command.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_command.dto.TransactionDTO;
import bantads.account_command.dto.TransactionRequestDTO;
import bantads.account_command.service.TransactionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/transaction")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

  @PostMapping
  public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequest){
    try{
      TransactionDTO transactionDTOCreated = transactionService.executeTransaction(transactionRequest);
      logger.info("Transaction created: %s of %.2f", transactionDTOCreated.getType(), transactionDTOCreated.getAmmount());
      return new ResponseEntity<>(transactionDTOCreated, HttpStatus.CREATED);
    }
    catch(Exception e){
      e.printStackTrace();
      logger.info("Error when creating Transaction", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
