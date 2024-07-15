package bantads.msclient.controllers;

import bantads.msclient.dto.ClientDTO;
import bantads.msclient.dto.LoginRequestDTO;
import bantads.msclient.dto.LoginResponseDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.ClientAlreadyExistsException;
import bantads.msclient.exception.InvalidCredentialsException;
import bantads.msclient.service.ClientService;
import bantads.msclient.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @PostMapping("/client")
    public ResponseEntity<Client> registerClient(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            Client newClient = clientService.createClient(clientDTO);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } catch (ClientAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO loginResponse = authService.authenticate(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().body("{ \"auth\": false, \"token\": null }");
    }
}
