package bantads.msclient.service;

import bantads.msclient.dto.ClientDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.ClientAlreadyExistsException;
import bantads.msclient.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Client createClient(ClientDTO clientDTO) {
        if (clientRepository.findByCpf(clientDTO.getCpf()).isPresent()) {
            throw new ClientAlreadyExistsException("Client with CPF already exists");
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        client.setSituation("OPEN");
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        client.setRole("CLIENT");
        return clientRepository.save(client);
    }
}
