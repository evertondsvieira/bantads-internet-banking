package bantads.saga_manager.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.saga_manager.dto.ManagerAccountDTO;
import bantads.saga_manager.dto.UserDTO;
import bantads.saga_manager.response.ErrorMessage;
import bantads.saga_manager.response.SuccessMessage;
import bantads.saga_manager.store.ManagerStore;

@Service
public class AuthService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverManagerTopic;

    @Autowired
    private ManagerStore managerStoreDTO;

    @Autowired
    private AccountService accountService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void createAuth(UserDTO userDTO) {
        try {
            String userStr = objectMapper.writeValueAsString(userDTO);
            template.convertAndSend(receiverManagerTopic.getName(), "authInsertRequest", userStr);
            System.out.println("Solicitação de cadastro de autenticação enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "authInsertReplyQueue")
    public void handleAuth(String mensagem) {
        try {
            JsonNode node = objectMapper.readTree(mensagem);
            String type = node.get("type").asText();

            if ("sucesso".equals(type)) {
                SuccessMessage successMessage = objectMapper.treeToValue(node, SuccessMessage.class);
                UserDTO userDTO = objectMapper.readValue(successMessage.getData(), UserDTO.class);
                String userStr = objectMapper.writeValueAsString(userDTO);

                System.out.println("Mensagem de cadastro de autenticação recebida com sucesso: ");
                System.out.println(userStr);
                
                System.out.println("AuthService envia ManagerDTO para AccountService");
                ManagerAccountDTO managerAccountDTO = managerStoreDTO.getManagerStoreDTO();
                accountService.createManager(managerAccountDTO);

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