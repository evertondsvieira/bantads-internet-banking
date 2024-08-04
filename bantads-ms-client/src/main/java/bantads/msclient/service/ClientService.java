package bantads.msclient.service;

import bantads.msclient.dto.UpdateClientDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.ClientAlreadyExistsException;
import bantads.msclient.exception.ClientNotFoundException;
import bantads.msclient.repository.ClientRepository;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired 
    private ModelMapper mapper;

    public Client createClient(Client newClient) {
        if (clientRepository.findByCpf(newClient.getCpf()).isPresent()) {
            throw new ClientAlreadyExistsException("Client with CPF already exists");
        }
        newClient.setSituation("PENDING");
        newClient.setRole("CLIENT");
        return clientRepository.save(newClient);
    }

    @Transactional
    public Client updateClient(Long id, UpdateClientDTO updateClientDTO) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found"));

        if(updateClientDTO.getAddress() != null){
            client.setAddress(mapper.map(updateClientDTO.getAddress(), bantads.msclient.entity.Address.class));
        }
        if(updateClientDTO.getName() != null){
            client.setName(updateClientDTO.getName());
        }
        if(updateClientDTO.getPhone() != null){
            client.setPhone(updateClientDTO.getPhone());
        }
        if(updateClientDTO.getSalary() != null){
            client.setSalary(updateClientDTO.getSalary());
        }
        if(updateClientDTO.getSituation() != null){
            client.setSituation(updateClientDTO.getSituation());
        }

        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<Client> getClientsBySituation(String situation) {
        return clientRepository.findBySituation(situation);
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() -> new ClientNotFoundException("Client with email not found"));
    }

    public List<Client> getClientsByCpf(String cpf) {
        return clientRepository.findListByCpf(cpf);
    }

    public List<Client> getClientsByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }
}
