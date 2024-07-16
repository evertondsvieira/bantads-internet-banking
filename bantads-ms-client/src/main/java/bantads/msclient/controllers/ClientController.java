package bantads.msclient.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bantads.msclient.dto.AddressDTO;
import bantads.msclient.dto.ClientDTO;
import bantads.msclient.dto.LoginRequestDTO;
import bantads.msclient.dto.LoginResponseDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.entity.Address;
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

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @PostMapping("/client")
    public ResponseEntity<Client> registerClient(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            Client newClient = new Client();
            newClient.setName(clientDTO.getName());
            newClient.setEmail(clientDTO.getEmail());
            newClient.setCpf(clientDTO.getCpf());
            newClient.setPhone(clientDTO.getPhone());
            newClient.setSalary(clientDTO.getSalary());
            newClient.setPassword(clientDTO.getPassword());

            Address address = new Address();
            AddressDTO addressDTO = clientDTO.getAddress();
            if (addressDTO != null) {
                address.setType(addressDTO.getType());
                address.setStreet(addressDTO.getStreet());
                address.setNumber(addressDTO.getNumber());
                address.setComplement(addressDTO.getComplement());
                address.setCep(addressDTO.getCep());
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
            }
            newClient.setAddress(address);

            Client createdClient = clientService.createClient(newClient);
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        } catch (ClientAlreadyExistsException e) {
            logger.error("Client already exists: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Error registering client: {}", e.getMessage());
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
