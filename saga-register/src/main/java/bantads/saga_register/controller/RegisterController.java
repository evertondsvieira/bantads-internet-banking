package bantads.saga_register.controller;

import bantads.saga_register.dto.ClientDTO;
import bantads.saga_register.service.SagaClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class RegisterController {

    @Autowired
    private SagaClientService sagaClientService;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientDTO clientDTO) {
        sagaClientService.createClient(clientDTO);
        try {
            return ResponseEntity.ok("Solicitação de cadastro recebida com sucesso");
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processamento da solicitação de cadastro");
        }
    }    
}