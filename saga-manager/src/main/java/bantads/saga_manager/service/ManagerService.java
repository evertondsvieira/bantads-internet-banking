package bantads.saga_manager.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import bantads.saga_manager.dto.ManagerDTO;
import bantads.saga_manager.dto.UserDTO;
import bantads.saga_manager.response.ErrorMessage;
import bantads.saga_manager.response.SuccessMessage;
import bantads.saga_manager.store.UserStore;

@Service
public class ManagerService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverManagerTopic;

    @Autowired
    private UserStore userStoreDTO;

    @Autowired
    private AuthService authService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void createManager(ManagerDTO managerDTO) {
        try {
            String managerStr = objectMapper.writeValueAsString(managerDTO);
            template.convertAndSend(receiverManagerTopic.getName(), "managerInsertRequest", managerStr);
            System.out.println("Solicitação de cadastro de gerente enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "managerInsertReplyQueue")
    public void handleManager(String mensagem) {
        try {
            JsonNode node = objectMapper.readTree(mensagem);
            String type = node.get("type").asText();

            if ("sucesso".equals(type)) {
                SuccessMessage successMessage = objectMapper.treeToValue(node, SuccessMessage.class);
                ManagerDTO managerDTO = objectMapper.readValue(successMessage.getData(), ManagerDTO.class);
                String managerStr = objectMapper.writeValueAsString(managerDTO);

                System.out.println("Mensagem de cadastro de gerente recebida com sucesso: ");
                System.out.println(managerStr);
                
                UserDTO userDTO = userStoreDTO.getUserStoreDTO();
                System.out.println("ManagerService envia UsuarioDTO para AuthService");
                authService.createAuth(userDTO);      

            } else if ("erro".equals(type)) {
                ErrorMessage errorMessage = objectMapper.treeToValue(node, ErrorMessage.class);
                String msgErroRetorno = errorMessage.getMessage();

                System.out.println("Mensagem de retorno: " + msgErroRetorno);

            } else {
                System.out.println("Tipo de mensagem desconhecido: " + type);
            }
        } catch (Exception e) {
            System.out.println("Erro no processamento da resposta");
            e.printStackTrace();
        }
    }
}