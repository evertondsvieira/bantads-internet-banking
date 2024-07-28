package bantads.account_command.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.enums.Event;
import bantads.account_command.exceptions.RecordDuplicationException;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.publisher.RabbitMQProducer;
import bantads.account_command.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;

	@PostMapping()
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
		try{
			AccountDTO accountDTOCreated = accountService.createAccount(accountDTO);
			return new ResponseEntity<>(accountDTOCreated, HttpStatus.CREATED);
		} 
    catch (RecordDuplicationException r) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } 
		catch (Exception e){
			e.printStackTrace();
			logger.error("Error creating account ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDTO> putMethodName(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
		try {
			AccountDTO accountDTOUpdated = accountService.updateAccount(id, accountDTO);
			return new ResponseEntity<>(accountDTOUpdated, HttpStatus.OK);
		}catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Error updating account ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}