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
import bantads.account_command.publisher.RabbitMQProducer;
import bantads.account_command.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	private RabbitMQProducer producer;

	AccountController(RabbitMQProducer producer){
		this.producer = producer;
	}
	
	@Autowired
	private AccountService accountService;

	@PostMapping()
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO account) {
		try{
			AccountDTO accountDTO = accountService.createAccount(account);
			producer.sendMessage(account, Event.CREATE_ACCOUNT);
			return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Error creating account ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
