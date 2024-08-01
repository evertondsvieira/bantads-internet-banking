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

@Service
public class DeleteManager {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderManagerTopic;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "managerDeleteRequestQueue")
    public void deleteManager(String mensagem) {

        String msgErro;
        ErrorMessage errorMessage;
        String errorStr;

        try {
            String emailManager = mensagem;

            Optional<Manager> manager = managerRepository.findByEmail(emailManager);
            if (manager.isPresent()) {
                Manager removedManager = manager.get();
                managerRepository.delete(removedManager);
                ManagerDTO managerDTO = convertToDTO(removedManager);
                String managerStr = objectMapper.writeValueAsString(managerDTO);
                SuccessMessage successMessage = new SuccessMessage(managerStr);
                String successStr = objectMapper.writeValueAsString(successMessage);             
                template.convertAndSend(senderManagerTopic.getName(), "managerDeleteReply", successStr);
                String mensagemResultado = "Gerente com email " + emailManager + " removido com sucesso";
                System.out.println(mensagemResultado);
            }
            else {
                msgErro = "Erro. Gerente com email " + emailManager + " nao encontrado para remocao";
                errorMessage = new ErrorMessage(msgErro);
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "managerDeleteReply", errorStr);
                System.out.println(errorStr);
            }
        } catch (Exception e) {
            msgErro = "Erro no processamento da remoção do gerente";
            errorMessage = new ErrorMessage(msgErro);
            try {
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "managerDeleteReply", errorStr);
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
}