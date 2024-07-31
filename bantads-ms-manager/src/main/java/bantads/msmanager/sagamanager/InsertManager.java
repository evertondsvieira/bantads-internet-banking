package bantads.msmanager.sagamanager;

import java.util.Optional;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.msmanager.dto.ManagerDTO;
import bantads.msmanager.entity.Manager;
import bantads.msmanager.repository.ManagerRepository;
import bantads.msmanager.sagamanager.response.ErrorMessage;
import bantads.msmanager.sagamanager.response.SuccessMessage;
import bantads.msmanager.service.ManagerService;

@Service
public class InsertManager {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderManagerTopic;

    @Autowired
    private ManagerService managerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "managerInsertRequestQueue")
    public void createManager(String mensagem) {

        String msgErro;
        ErrorMessage errorMessage;
        String errorStr;

        try {
            ManagerDTO managerDTO = objectMapper.readValue(mensagem, ManagerDTO.class);

            Optional<Manager> emailExistingManager = managerRepository.findByEmail(managerDTO.getEmail());
            if (emailExistingManager.isPresent()) {
                msgErro = "Erro no cadastro de gerente. Email já cadastrado";
                errorMessage = new ErrorMessage(msgErro);
                errorStr = objectMapper.writeValueAsString(errorMessage);             
                template.convertAndSend(senderManagerTopic.getName(), "managerInsertReply", errorStr);
                System.out.println(errorStr);
            }
            else {
                Manager createdManager = managerService.createManager(convertToEntity(managerDTO));
                ManagerDTO createdManagerDTO = convertToDTO(createdManager);
                String managerStr = objectMapper.writeValueAsString(createdManagerDTO);
                SuccessMessage successMessage = new SuccessMessage(managerStr);
                String successStr = objectMapper.writeValueAsString(successMessage);
                template.convertAndSend(senderManagerTopic.getName(), "managerInsertReply", successStr);
                System.out.println("Cadastro de gerente realizado com sucesso");
            }
        } catch (Exception e) {
            msgErro = "Erro no processamento do cadastro";
            errorMessage = new ErrorMessage(msgErro);
            try {
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "managerInsertReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }

    private ManagerDTO convertToDTO(Manager manager) {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName(manager.getName());
        managerDTO.setCpf(manager.getCpf());
        managerDTO.setEmail(manager.getEmail());
        managerDTO.setPhone(manager.getPhone());

        return managerDTO;
    }

    private Manager convertToEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setName(managerDTO.getName());
        manager.setEmail(managerDTO.getEmail());
        manager.setCpf(managerDTO.getCpf());
        manager.setPhone(managerDTO.getPhone());

        return manager;
    }
}