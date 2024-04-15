package bantads.msclient.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.msclient.DTO.ClientDTO;
import bantads.msclient.model.Client;
import bantads.msclient.repository.ClientRepository;

@Service
public class ClientService {
  
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ModelMapper mapper;
  
  public ClientDTO createClient(ClientDTO clientDTO) {
    Client client = mapper.map(clientDTO, Client.class);
    clientRepository.save(client);
    return clientDTO;
  }
}
