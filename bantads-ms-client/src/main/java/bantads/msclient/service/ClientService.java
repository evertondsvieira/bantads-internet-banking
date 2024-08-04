package bantads.msclient.service;

import bantads.msclient.dto.UpdateClientDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.ClientAlreadyExistsException;
import bantads.msclient.exception.ClientNotFoundException;
import bantads.msclient.repository.ClientRepository;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired 
    private ModelMapper mapper;

    @Autowired
    private JavaMailSender mailSender;

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
            System.out.println(updateClientDTO.getSituation());
            System.out.println(client.getSituation());
            if(!updateClientDTO.getSituation().equalsIgnoreCase(client.getSituation())){
                System.out.println("Manda o email");
                sendMessage(updateClientDTO.getSituation(), client.getEmail());
            }
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

    private void sendMessage(String situation, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bantads@email.com");
        message.setTo(email);
        if(situation.equals("APPROVED")){
            message.setSubject("BANTADS - Sua conta foi aprovada");
            message.setText("Pode acessar sua conta com email e senha recebidos na etapa de cadastro");
        }
        if(situation.equals("REJECTED")){
            message.setSubject("BANTADS - Sua conta foi rejeitada");
            message.setText("Entre em contato com seu gerente BANTADS para saber mais detalhes");
        }
        mailSender.send(message);
    }

}
