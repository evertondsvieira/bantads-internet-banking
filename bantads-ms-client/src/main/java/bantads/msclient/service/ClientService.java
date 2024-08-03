package bantads.msclient.service;

import bantads.msclient.dto.UpdateClientDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.ClientAlreadyExistsException;
import bantads.msclient.exception.ClientNotFoundException;
import bantads.msclient.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

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

        BeanUtils.copyProperties(updateClientDTO, client);

        if (updateClientDTO.getSalary() != null) {
            client.setSalary(updateClientDTO.getSalary());
            double newLimit = calculateLimit(updateClientDTO.getSalary());
            double currentBalance = getBalance(client);
            if (newLimit < 0 && newLimit < currentBalance) {
                newLimit = currentBalance;
            }
        }

        return clientRepository.save(client);
    }

    private double calculateLimit(double salary) {
        return salary * 0.3;
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    private double getBalance(Client client) {
        return 0.0;
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
