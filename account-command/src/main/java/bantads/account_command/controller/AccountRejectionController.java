package bantads.account_command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_command.dto.AccountRejectionDTO;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.service.AccountRejectionService;

@RestController
public class AccountRejectionController {

  @Autowired
  private AccountRejectionService accountRejectionService;

  @PostMapping("/account/{id}/reject")
  public ResponseEntity<String> executeRejection(
    @PathVariable("id") Long accountId, 
    @RequestBody AccountRejectionDTO accountRejectionDTO)
  {
    try {
      String response = accountRejectionService.executeRejection(accountId, accountRejectionDTO);
      return new ResponseEntity<String>(response,HttpStatus.CREATED);
    } catch (RecordNotFoundException e){
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
}
