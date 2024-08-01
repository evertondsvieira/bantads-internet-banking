package bantads.msclient.controllers;

import bantads.msclient.dto.AddressDTO;
import bantads.msclient.dto.ClientDTO;
import bantads.msclient.dto.UpdateClientDTO;
import bantads.msclient.exception.ClientAlreadyExistsException;
import bantads.msclient.service.ClientService;
import bantads.msclient.entity.Address;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.ClientNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public ResponseEntity<List<ClientDTO>> getAllClients(@RequestParam(required = false) String situation) {
        try {
            List<ClientDTO> clientDTOs = (situation != null ? clientService.getClientsBySituation(situation)
                    : clientService.getAllClients())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(clientDTOs);
        } catch (Exception e) {
            logger.error("Error fetching clients: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        try {
            ClientDTO clientDTO = convertToDTO(clientService.getClientById(id)
                    .orElseThrow(() -> new ClientNotFoundException("Client not found")));

            return ResponseEntity.ok(clientDTO);
        } catch (ClientNotFoundException e) {
            logger.error("Client not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error fetching client by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/client")
    public ResponseEntity<ClientDTO> registerClient(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            Client createdClient = clientService.createClient(convertToEntity(clientDTO));
            return new ResponseEntity<>(convertToDTO(createdClient), HttpStatus.CREATED);
        } catch (ClientAlreadyExistsException e) {
            logger.error("Client already exists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            logger.error("Error registering client: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody UpdateClientDTO updateClientDTO) {
        try {
            Client updatedClient = clientService.updateClient(id, updateClientDTO);
            return ResponseEntity.ok(convertToDTO(updatedClient));
        } catch (ClientNotFoundException e) {
            logger.error("Client not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error updating client: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId()); 
        clientDTO.setName(client.getName());
        clientDTO.setCpf(client.getCpf());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setSalary(client.getSalary());
        clientDTO.setSituation(client.getSituation());
        clientDTO.setRole(client.getRole());

        Address address = client.getAddress();
        if (address != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setType(address.getType());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setNumber(address.getNumber());
            addressDTO.setComplement(address.getComplement());
            addressDTO.setCep(address.getCep());
            addressDTO.setCity(address.getCity());
            addressDTO.setState(address.getState());
            clientDTO.setAddress(addressDTO);
        }

        return clientDTO;
    }

    private Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setCpf(clientDTO.getCpf());
        client.setPhone(clientDTO.getPhone());
        client.setSalary(clientDTO.getSalary());

        AddressDTO addressDTO = clientDTO.getAddress();
        if (addressDTO != null) {
            Address address = new Address();
            address.setType(addressDTO.getType());
            address.setStreet(addressDTO.getStreet());
            address.setNumber(addressDTO.getNumber());
            address.setComplement(addressDTO.getComplement());
            address.setCep(addressDTO.getCep());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            client.setAddress(address);
        }

        return client;
    }
}
