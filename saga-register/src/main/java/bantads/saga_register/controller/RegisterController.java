package bantads.saga_register.controller;

import bantads.saga_register.dto.ClientDTO;
import bantads.saga_register.dto.ResponseDTO;
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
    public ResponseEntity<Object> registerClient(@RequestBody ClientDTO clientDTO) {
        try {
            sagaClientService.createClient(clientDTO);
            return ResponseEntity.ok(
                new ResponseDTO("Solicitação de cadastro recebida com sucesso", true)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseDTO("Erro no processamento da solicitação de cadastro", false)
            );
        }
    }
}