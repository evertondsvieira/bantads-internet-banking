package bantads.msaccount.service;

import bantads.msaccount.dto.ClientDTO;
import bantads.msaccount.entity.Client;
import bantads.msaccount.exception.ClientAlreadyExistsException;
import bantads.msaccount.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(ClientDTO clientDTO) {
        if (clientRepository.findByCpf(clientDTO.getCpf()).isPresent()) {
            throw new ClientAlreadyExistsException("Client with CPF already exists");
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        client.setSituation("OPEN");
        return clientRepository.save(client);
    }
}
