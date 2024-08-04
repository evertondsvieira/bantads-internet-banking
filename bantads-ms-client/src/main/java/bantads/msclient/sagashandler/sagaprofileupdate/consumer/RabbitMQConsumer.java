package bantads.msclient.sagashandler.sagaprofileupdate.consumer;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.msclient.dto.AddressDTO;
import bantads.msclient.dto.ClientDTO;
import bantads.msclient.dto.UpdateClientDTO;
import bantads.msclient.entity.Address;
import bantads.msclient.entity.Client;
import bantads.msclient.sagashandler.sagaprofileupdate.publisher.RabbitMQProducer;
import bantads.msclient.service.ClientService;

@Service
public class RabbitMQConsumer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

  @Autowired
  private ClientService clientService;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private RabbitMQProducer producer;

  @RabbitListener(queues = "update.client.queue")
  public void handleClientUpdateQueue(ClientDTO clientDTO){
    try{
      LOGGER.info("Message Received -> %s ", clientDTO.toString());
      System.out.println(clientDTO);
      // ClientDTO clientDTO = objectMapper.readValue(clientDTOMessage, ClientDTO.class);
      UpdateClientDTO updateClientDTO = mapper.map(clientDTO, UpdateClientDTO.class);
      Client client = clientService.updateClient(clientDTO.getId(), updateClientDTO);
      LOGGER.info(String.format("Client Was updated succefully -> %s", clientDTO.toString()));
      producer.sendMessage(convertToDTO(client));
    } catch (Exception e){
      e.printStackTrace();
      LOGGER.info(String.format("Error -> %s", e.getMessage()));
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
}
