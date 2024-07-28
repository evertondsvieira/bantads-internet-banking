package bantads.account_query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_query.dto.AccountDTO;
import bantads.account_query.exceptions.RecordNotFoundException;
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
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<AccountDTO>> getAccounts(){
    try{
      List<AccountDTO> accountDTOs = accountService.getAccounts();
      return ResponseEntity.ok(accountDTOs);
    }
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }  
    catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
  
  @GetMapping("/client/{cpf}")
  public ResponseEntity<AccountDTO> getAccountByClientCpf(@PathVariable String cpf){
    try{
      AccountDTO accountDTO = accountService.getAccountByClientCpf(cpf);
      return ResponseEntity.ok(accountDTO);
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/manager/{cpf}")
  public ResponseEntity<List<AccountDTO>> getAccountsByManagerCpf(@PathVariable String cpf){
    try{
      List<AccountDTO> accountDTOs = accountService.getAccountsByManagerCpf(cpf);
      return ResponseEntity.ok(accountDTOs);
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
