package bantads.saga_manager.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import bantads.saga_manager.dto.ManagerAccountDTO;
import bantads.saga_manager.response.ErrorMessage;
import bantads.saga_manager.response.SuccessMessage;

@Service
public class AccountService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverManagerTopic;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void createManager(ManagerAccountDTO managerAccountDTO) {
        try {
            String managerStr = objectMapper.writeValueAsString(managerAccountDTO);
            template.convertAndSend(receiverManagerTopic.getName(), "accountInsertRequest", managerStr);
            System.out.println("Solicitação de cadastro de gerente no MS Account enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "accountInsertReplyQueue")
    public void handleManager(String mensagem) {
        try {
            if (mensagem.contains("sucesso")) {
                System.out.println("Mensagem de cadastro de gerente no MS Account recebida com sucesso: ");
                System.out.println(mensagem);
                
                System.out.println("SAGA de inserção de gerente finalizada com sucesso");                

            } else {
                System.out.println("Erro no processamento do cadastro de gerente no MS Account");
            }
        } catch (Exception e) {
            System.out.println("Erro no processamento da resposta");
            e.printStackTrace();
        }
    }
}