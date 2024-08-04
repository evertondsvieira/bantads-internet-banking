package bantads.msclient.sagashandler.sagaregister;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.msclient.dto.AddressDTO;
import bantads.msclient.dto.ClientDTO;
import bantads.msclient.dto.ClientSagaDTO;
import bantads.msclient.service.ClientService;
import bantads.msclient.entity.Address;
import bantads.msclient.entity.Client;
import bantads.msclient.repository.ClientRepository;
import bantads.msclient.sagashandler.sagaregister.response.ErrorMessage;
import bantads.msclient.sagashandler.sagaregister.response.SuccessMessage;

@Service
public class ClientRegister {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderTopic;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "clientRequestQueue")
    public void createClient(String mensagem) {

        String msgErro;
        ErrorMessage errorMessage;
        String errorStr;

        try {
            ClientDTO clientDTO = objectMapper.readValue(mensagem, ClientDTO.class);

            Optional<Client> cpfExistingClient = clientRepository.findByCpf(clientDTO.getCpf());
            Optional<Client> emailExistingClient = clientRepository.findByEmail(clientDTO.getEmail());
            if (cpfExistingClient.isPresent() || emailExistingClient.isPresent()) {
                msgErro = "Erro no cadastro de cliente. CPF/Email já cadastrado";
                errorMessage = new ErrorMessage(msgErro);
                errorStr = objectMapper.writeValueAsString(errorMessage);             
                template.convertAndSend(senderTopic.getName(), "clientReply", errorStr);
                System.out.println(errorStr);
            }
            else {
                Client createdClient = clientService.createClient(convertToEntity(clientDTO));
                ClientDTO createdClientDTO = convertToDTO(createdClient);
                ClientSagaDTO clientSagaDTO = new ClientSagaDTO(createdClientDTO.getName(), createdClientDTO.getEmail(), createdClientDTO.getCpf(), createdClientDTO.getAddress(), createdClientDTO.getPhone(), createdClientDTO.getSalary());
                String clientStr = objectMapper.writeValueAsString(clientSagaDTO);
                SuccessMessage successMessage = new SuccessMessage(clientStr);
                String successStr = objectMapper.writeValueAsString(successMessage);
                template.convertAndSend(senderTopic.getName(), "clientReply", successStr);
                System.out.println("Cadastro de cliente realizado com sucesso");
            }
        } catch (Exception e) {
            msgErro = "Erro no processamento do cadastro";
            errorMessage = new ErrorMessage(msgErro);
            try {
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderTopic.getName(), "clientReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }
    
    private ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setCpf(client.getCpf());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setSalary(client.getSalary());

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