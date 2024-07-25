package bantads.account_query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_query.dto.AccountDTO;
import bantads.account_query.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
  
  @Autowired
  private AccountService accountService;

  @GetMapping("/{id}")
  public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){
    try{
      AccountDTO accountDTO = accountService.getAccountById(id);
      return ResponseEntity.ok(accountDTO);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
  }
}
