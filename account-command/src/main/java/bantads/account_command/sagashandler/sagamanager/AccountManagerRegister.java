package bantads.account_command.sagashandler.sagamanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.ManagerDTO;
import bantads.account_command.sagashandler.sagaregister.response.ErrorMessage;
import bantads.account_command.sagashandler.sagaregister.response.SuccessMessage;
import bantads.account_command.service.ManagerService;

@Service
public class AccountManagerRegister {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderManagerTopic;

    @Autowired
    private ManagerService managerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "accountInsertRequestQueue")
    public void createManager(String mensagem) {

        try {
            ManagerDTO managerDTO = objectMapper.readValue(mensagem, ManagerDTO.class);            
            ManagerDTO createdManagerDTO = managerService.createManager(managerDTO);
            String managerStr = objectMapper.writeValueAsString(createdManagerDTO);
            SuccessMessage successMessage = new SuccessMessage(managerStr);
            String successStr = objectMapper.writeValueAsString(successMessage);
            template.convertAndSend(senderManagerTopic.getName(), "accountInsertReply", successStr);
            System.out.println("Cadastro de gerente no ms conta realizado com sucesso");
        } catch (Exception e) {
            String msgErro = "Erro no processamento do cadastro";
            ErrorMessage errorMessage = new ErrorMessage(msgErro);
            try {
                String errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "accountInsertReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }
    
}